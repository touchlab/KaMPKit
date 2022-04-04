package co.touchlab.kampkit.models

import co.touchlab.kampkit.DatabaseHelper
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.DogApi
import co.touchlab.kermit.Logger
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class BreedRepository(
    private val dbHelper: DatabaseHelper,
    private val settings: Settings,
    private val dogApi: DogApi,
    log: Logger,
    private val clock: Clock
) {

    private val log = log.withTag("BreedModel")

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
        ensureNeverFrozen()
    }

    fun getBreeds(): Flow<List<Breed>> = dbHelper.selectAllItems()

    suspend fun refreshBreedsIfStale() {
        if (isBreedListStale()) {
            refreshBreeds()
        }
    }

    suspend fun refreshBreeds() {
        val breedResult = dogApi.getJsonFromApi()
        log.v { "Breed network result: ${breedResult.status}" }
        val breedList = breedResult.message.keys.sorted().toList()
        log.v { "Fetched ${breedList.size} breeds from network" }
        settings.putLong(DB_TIMESTAMP_KEY, clock.now().toEpochMilliseconds())

        if (breedList.isNotEmpty()) {
            dbHelper.insertBreeds(breedList)
        }
    }

    suspend fun updateBreedFavorite(breed: Breed) {
        dbHelper.updateFavorite(breed.id, !breed.favorite)
    }

    private fun isBreedListStale(): Boolean {
        val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
        val oneHourMS = 60 * 60 * 1000
        val stale = lastDownloadTimeMS + oneHourMS < clock.now().toEpochMilliseconds()
        if (!stale) {
            log.i { "Breeds not fetched from network. Recently updated" }
        }
        return stale
    }
}
