package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver

actual fun testDbConnection(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")