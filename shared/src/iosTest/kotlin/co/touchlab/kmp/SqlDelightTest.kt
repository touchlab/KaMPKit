package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal actual fun testDbConnection(): SqlDriver = NativeSqliteDriver(KampstarterDb.Schema, "kampstarterdb")