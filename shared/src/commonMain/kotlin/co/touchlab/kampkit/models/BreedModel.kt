package co.touchlab.kampkit.models

import co.touchlab.kampkit.DatabaseHelper
import co.touchlab.kampkit.currentTimeMillis
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.KtorApi
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class BreedModel() : KoinComponent {
    private val dbHelper: DatabaseHelper by inject()
    private val settings: Settings by inject()
    private val ktorApi: KtorApi by inject()
    private val log: Kermit by inject { parametersOf("BreedModel") }

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
        ensureNeverFrozen()
    }

    fun selectAllBreeds() =
        dbHelper.selectAllItems()
            .map { itemList ->
                log.v { "Select all query dirtied" }
                ItemDataSummary(
                    itemList.maxByOrNull { it.name.length },
                    itemList
                )
            }

    suspend fun getBreedsFromNetwork(): String? {
        fun isBreedListStale(currentTimeMS: Long): Boolean {
            val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
            val oneHourMS = 60 * 60 * 1000
            return (lastDownloadTimeMS + oneHourMS < currentTimeMS)
        }

        val currentTimeMS = currentTimeMillis()
        if (isBreedListStale(currentTimeMS)) {
            try {
                val breedResult = ktorApi.getJsonFromApi()
                log.v { "Breed network result: ${breedResult.status}" }
                val breedList = breedResult.message.keys.toList()
                log.v { "Fetched ${breedList.size} breeds from network" }
                dbHelper.insertBreeds(breedList)
                settings.putLong(DB_TIMESTAMP_KEY, currentTimeMS)
            } catch (e: Exception) {
                return "Unable to download breed list"
            }
        } else {
            log.i { "Breeds not fetched from network. Recently updated" }
        }
        return null
    }

    suspend fun updateBreedFavorite(breed: Breed) {
        dbHelper.updateFavorite(breed.id, breed.favorite != 1L)
    }
}

data class ItemDataSummary(val longestItem: Breed?, val allItems: List<Breed>)
