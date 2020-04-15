package co.touchlab.kampstarter

import android.content.Context
import android.content.SharedPreferences
import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
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

    single<Settings> {
        val context: Context = get()
        val preferences: SharedPreferences =
            context.getSharedPreferences("KAMPSTARTER_SETTINGS", Context.MODE_PRIVATE)
        AndroidSettings(preferences)
    }
}