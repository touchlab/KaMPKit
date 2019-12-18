package co.touchlab.kampstarter

import com.squareup.sqldelight.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main
internal actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}

@JvmName("toFlow")
actual fun <T : Any, R> Query<T>.asFlowWithTransform(transform:(Query<T>)->R): Flow<R> = flow {
    emit(transform(this@asFlowWithTransform))

    val channel = Channel<Unit>(CONFLATED)
    val listener = object : Query.Listener {
        override fun queryResultsChanged() {
            channel.offer(Unit)
        }
    }

    addListener(listener)
    try {
        for (item in channel) {
            emit(transform(this@asFlowWithTransform))
        }
    } finally {
        println("****** Closing Flow in removeListener ******")
        removeListener(listener)
    }
}