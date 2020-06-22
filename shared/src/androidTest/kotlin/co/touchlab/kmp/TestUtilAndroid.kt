package co.touchlab.kampstarter

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(KampstarterDb.Schema, app, "droidcondb")
}
