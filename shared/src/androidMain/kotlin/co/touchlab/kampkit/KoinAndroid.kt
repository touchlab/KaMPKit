package co.touchlab.kampkit

import co.touchlab.kampkit.db.KaMPKitDb
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
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
        AndroidSettings(get())
    }

    single {
        OkHttp.create()
    }
}
