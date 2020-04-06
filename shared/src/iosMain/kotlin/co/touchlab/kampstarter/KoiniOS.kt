package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun initKoin() = initKoin{}
actual val platformModule = module {
    single<Settings> {
        val userDefaults = NSUserDefaults(suiteName = "KAMPSTARTER_SETTINGS")
        AppleSettings(userDefaults)
    }
    single<SqlDriver> { NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb") }
}