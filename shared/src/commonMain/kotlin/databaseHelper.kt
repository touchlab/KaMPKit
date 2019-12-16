package com.touchlab.shared
import com.squareup.sqldelight.db.SqlDriver


object DatabaseHelper {

    fun initDatabase(sqlDriver: SqlDriver) {

        /*
        driverRef.value = sqlDriver.freeze()
        dbRef.value = DroidconDb(sqlDriver, Session.Adapter(
            startsAtAdapter = DateAdapter(), endsAtAdapter = DateAdapter()
        )).freeze()*/
    }

    internal fun dbClear() {
        //dbRef.value = null
        //driverRef.value?.close()
        //driverRef.value = null
    }
}
