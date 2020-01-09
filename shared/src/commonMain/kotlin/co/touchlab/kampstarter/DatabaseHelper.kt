package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(private val sqlDriver: SqlDriver) {
    private val dbRef: KampstarterDb = KampstarterDb(sqlDriver)

    internal fun dbClear() {
        sqlDriver.close()
    }

    fun selectAllItems(): Query<Breed> = dbRef.tableQueries.selectAll()

    suspend fun insertBreeds(breedNames: List<String>) = withContext(Dispatchers.Default) {
        dbRef.transaction {
            breedNames.forEach { name ->
                dbRef.tableQueries.insertBreed(null, name, 0)
            }
        }
    }

    suspend fun selectById(id: Long): Query<Breed> =
        withContext(Dispatchers.Default) { dbRef.tableQueries.selectById(id) }

    suspend fun deleteAll() = withContext(Dispatchers.Default) {
        dbRef.tableQueries.deleteAll()
    }

    suspend fun updateFavorite(breedId: Long, favorite: Boolean) = withContext(Dispatchers.Default) {
        dbRef.tableQueries.updateFavorite(favorite.toLong(), breedId)
    }
}

fun Breed.isFavorited(): Boolean = this.favorite != 0L
fun Boolean.toLong(): Long = if (this) 1L else 0L