package com.touchlab.shared

import co.touchlab.kampstarter.db.KampstarterDb
import co.touchlab.kampstarter.db.Items
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver


class DatabaseHelper(val sqlDriver: SqlDriver) {

    private var driverRef:SqlDriver? = null
    private var dbRef:KampstarterDb? = null

    init {
        driverRef = sqlDriver
        dbRef = KampstarterDb(sqlDriver)
    }

    internal fun dbClear() {
        driverRef?.close()
    }

    fun getTableQueries(): Query<Items> = dbRef!!.tableQueries.selectAll()

    fun getRowForIdQuery(id: Long): Query<Items> = dbRef!!.tableQueries.selectById(id)

    fun setRow(id: Long, value: String) = dbRef!!.tableQueries.insertRoot(id,value)

}
