package co.touchlab.kampstarter.android

import android.app.Application
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.ServiceRegistry
import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.android.AndroidSqliteDriver

class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()
        ServiceRegistry.appStart(
            DatabaseHelper(
                AndroidSqliteDriver(
                    KampstarterDb.Schema,
                    this,
                    "KampStarterDb"
                )
            ),
            AndroidSettings.Factory(this).create("KAMPSTARTER_SETTINGS")
        )
    }
}