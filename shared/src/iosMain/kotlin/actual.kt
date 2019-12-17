package com.touchlab.shared

import co.touchlab.droidcon.db.KampstarterDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import platform.UIKit.UIDevice

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}

@Suppress("unused")
fun defaultDriver(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")
