package com.touchlab.shared

import co.touchlab.kampstarter.db.KampstarterDb
import co.touchlab.kampstarter.db.Items
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver


class DatabaseHelper(val sqlDriver: SqlDriver) {

    private val driverRef:SqlDriver = sqlDriver
    private val dbRef:KampstarterDb = KampstarterDb(sqlDriver)

    internal fun dbClear() {
        driverRef.close()
    }

    fun selectAllItems(): Query<Items> = dbRef.tableQueries.selectAll()

    fun selectItemById(id: Long): Query<Items> = dbRef.tableQueries.selectById(id)

    fun insertItem(id: Long, value: String) = dbRef.tableQueries.insertRoot(id,value)

}
