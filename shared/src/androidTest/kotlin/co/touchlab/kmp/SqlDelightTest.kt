package co.touchlab.kampstarter

import android.app.Application
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import androidx.test.core.app.ApplicationProvider

actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(KampstarterDb.Schema, app, "droidcondb")
}