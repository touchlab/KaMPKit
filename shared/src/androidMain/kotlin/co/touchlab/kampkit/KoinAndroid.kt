package co.touchlab.kampkit

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import co.touchlab.kampkit.db.KaMPKitDb
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            KaMPKitDb.Schema,
            get(),
            "KampkitDb"
        )
    }

    single<Settings> {
        SharedPreferencesSettings(get())
    }

    single {
        OkHttp.create()
    }
}
