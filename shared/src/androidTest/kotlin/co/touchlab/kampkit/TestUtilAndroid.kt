package co.touchlab.kampkit

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import co.touchlab.kampkit.db.KaMPKitDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

internal actual fun testDbConnection(): SqlDriver {
    // Try to use the android driver (which only works if we're on robolectric).
    // Fall back to jdbc if that fails.
    return try {
        val app = ApplicationProvider.getApplicationContext<Application>()
        AndroidSqliteDriver(KaMPKitDb.Schema, app, "kampkitdb")
    } catch (exception: IllegalStateException) {
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { KaMPKitDb.Schema.create(it) }
    }
}
