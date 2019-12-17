package com.touchlab.shared

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import platform.UIKit.UIDevice
import co.touchlab.kampstarter.db.KampstarterDb
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}

fun defaultDriver(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")

internal actual val MainDispatcher:CoroutineDispatcher = object : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue().freeze()) {
            block.run()
        }
    }
}