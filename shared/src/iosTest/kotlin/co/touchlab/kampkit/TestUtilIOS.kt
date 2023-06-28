package co.touchlab.kampkit

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import co.touchlab.kampkit.db.KaMPKitDb

internal actual fun testDbConnection(): SqlDriver {
    return inMemoryDriver(KaMPKitDb.Schema)
}
