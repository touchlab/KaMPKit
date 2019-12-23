package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlinx.coroutines.runBlocking

actual fun testDbConnection(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")

actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }
