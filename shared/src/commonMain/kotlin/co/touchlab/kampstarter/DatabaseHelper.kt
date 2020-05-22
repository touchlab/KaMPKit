package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.db.KampstarterDb
import co.touchlab.kermit.Kermit
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(sqlDriver: SqlDriver, private val log: Kermit) {
    private val dbRef: KampstarterDb = KampstarterDb(sqlDriver)

    fun selectAllItems(): Query<Breed> = dbRef.tableQueries.selectAll()

    suspend fun insertBreeds(breedNames: List<String>) = withContext(Dispatchers.Default) {
        log.d { "Inserting ${breedNames.size} breeds into database" }
        dbRef.transaction {
            breedNames.forEach { name ->
                dbRef.tableQueries.insertBreed(null, name, 0)
            }
        }
    }

    suspend fun selectById(id: Long): Query<Breed> =
        withContext(Dispatchers.Default) { dbRef.tableQueries.selectById(id) }

    suspend fun deleteAll() = withContext(Dispatchers.Default) {
        log.i { "Database Cleared" }
        dbRef.tableQueries.deleteAll()
    }

    suspend fun updateFavorite(breedId: Long, favorite: Boolean) = withContext(Dispatchers.Default) {
        log.i { "Breed $breedId: Favorited $favorite" }
        dbRef.tableQueries.updateFavorite(favorite.toLong(), breedId)
    }
}

fun Breed.isFavorited(): Boolean = this.favorite != 0L
internal fun Boolean.toLong(): Long = if (this) 1L else 0L