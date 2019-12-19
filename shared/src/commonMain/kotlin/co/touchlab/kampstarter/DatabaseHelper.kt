package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver

class DatabaseHelper(private val sqlDriver: SqlDriver) {
    private val dbRef:KampstarterDb = KampstarterDb(sqlDriver)

    internal fun dbClear() {
        sqlDriver.close()
    }

    fun selectAllItems(): Query<Breed> = dbRef.tableQueries.selectAll()

    fun selectItemById(id: Long): Query<Breed> = dbRef.tableQueries.selectById(id)

    fun insertItem(id: Long, value: String) = dbRef.tableQueries.insertBreed(id,value)

}