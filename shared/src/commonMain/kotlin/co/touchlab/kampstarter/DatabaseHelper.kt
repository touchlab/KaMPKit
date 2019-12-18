package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.JvmName

class DatabaseHelper(private val sqlDriver: SqlDriver) {
    private val dbRef:KampstarterDb = KampstarterDb(sqlDriver)

    internal fun dbClear() {
        sqlDriver.close()
    }

    fun selectAllItems(): Query<Items> = dbRef.tableQueries.selectAll()

    fun selectItemById(id: Long): Query<Items> = dbRef.tableQueries.selectById(id)

    fun insertItem(id: Long, value: String) = dbRef.tableQueries.insertRoot(id,value)

}

@JvmName("toFlow")
expect fun <T : Any, R> Query<T>.asFlowWithTransform(transform:(Query<T>)->R): Flow<R>