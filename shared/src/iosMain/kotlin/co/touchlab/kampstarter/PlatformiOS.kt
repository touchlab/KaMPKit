package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

fun defaultDriver(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")

fun defaultSettings(): Settings = AppleSettings.Factory().create("KAMPSTARTER_SETTINGS")

internal actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}
