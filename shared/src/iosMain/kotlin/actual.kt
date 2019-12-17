package com.touchlab.shared

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import platform.UIKit.UIDevice

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}

@Throws
fun defaultDriver(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")
