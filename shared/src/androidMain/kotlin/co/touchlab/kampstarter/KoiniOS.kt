package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            KampstarterDb.Schema,
            get(),
            "KampStarterDb"
        )
    }

    single { AndroidSettings.Factory(get()).create("KAMPSTARTER_SETTINGS") }
}