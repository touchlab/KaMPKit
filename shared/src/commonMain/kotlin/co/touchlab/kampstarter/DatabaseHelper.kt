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

    fun insertBreed(name: String) = dbRef.tableQueries.insertBreed(null,name,0)

    fun updateFavorite(breedId: Long, favorite: Boolean) = dbRef.tableQueries.updateFavorite(favorite.toLong(),breedId)

    fun deleteAll(){
        dbRef.tableQueries.deleteAll()
    }
}

fun Breed.isFavorited(): Boolean = this.favorite != 0L
fun Boolean.toLong(): Long = if(this) 1L else 0L