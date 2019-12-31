package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AppleSettings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import org.koin.dsl.module

fun initKoin() = initKoin{}
actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb") }
    single { AppleSettings.Factory().create("KAMPSTARTER_SETTINGS") }
}