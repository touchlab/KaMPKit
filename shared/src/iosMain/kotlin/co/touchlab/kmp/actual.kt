package co.touchlab.kmp

import co.touchlab.kampstarter.db.KampstarterDb
import com.autodesk.coroutineworker.CoroutineWorker
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlinx.cinterop.StableRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ensureNeverFrozen
import kotlin.native.concurrent.freeze

fun defaultDriver(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")

fun defaultSettings(): Settings = AppleSettings.Factory().create("KAMPSTARTER_SETTINGS")

internal actual val MainDispatcher:CoroutineDispatcher = object : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue().freeze()) {
            block.run()
        }
    }
}

internal actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}

actual fun <T : Any, R> Query<T>.asFlowWithTransform(transform:(Query<T>)->R): Flow<R> {
    val flowDef = flow {
        val r = CoroutineWorker.withContext(Dispatchers.Default) {
            transform(this@asFlowWithTransform)
        }

        emit(r)

        val channel = Channel<Unit>(CONFLATED)
        val channelRef = StableRef.create(channel).freeze()

        val listener = object : Query.Listener {
            override fun queryResultsChanged() {
                dispatch_async(dispatch_get_main_queue().freeze()) {
                    channelRef.get().offer(Unit)
                }
            }
        }

        addListener(listener)
        try {
            for (item in channel) {
                val r = CoroutineWorker.withContext(Dispatchers.Default) {
                    transform(this@asFlowWithTransform)
                }
                emit(r)
            }
        } finally {
            println("****** Closing Flow in removeListener ******")
            removeListener(listener)
        }
    }

    flowDef.ensureNeverFrozen()

    return flowDef
}