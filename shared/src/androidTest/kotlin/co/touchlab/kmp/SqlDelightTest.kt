package co.touchlab.kampstarter

import android.app.Application
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith

actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }

actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(KampstarterDb.Schema, app, "droidcondb")
}

@RunWith(AndroidJUnit4::class)
class SqlDelightTestJvm : SqlDelightTest()

@RunWith(AndroidJUnit4::class)
class KtorTestJvm: KtorTest()

@RunWith(AndroidJUnit4::class)
class BreedModelTestJvm: BreedModelTest()