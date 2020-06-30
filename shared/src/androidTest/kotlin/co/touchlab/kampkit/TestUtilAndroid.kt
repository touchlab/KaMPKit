package co.touchlab.kampkit

import android.app.Application
import co.touchlab.kampkit.db.KaMPKitDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import androidx.test.core.app.ApplicationProvider
internal actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(KaMPKitDb.Schema, app, "kampkitdb")
}