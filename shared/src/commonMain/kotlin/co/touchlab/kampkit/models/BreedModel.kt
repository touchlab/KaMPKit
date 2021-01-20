package co.touchlab.kampkit.models

import co.touchlab.kampkit.DatabaseHelper
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.KtorApi
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class BreedModel : KoinComponent {
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

    fun getBreeds(): Flow<DataState<ItemDataSummary>> = flow {
        emit(DataState.Loading)
        val currentTimeMS = Clock.System.now().toEpochMilliseconds()
        val stale = isBreedListStale(currentTimeMS)
        val networkBreedDataState: DataState<ItemDataSummary>
        if (stale) {
            networkBreedDataState = getBreedsFromNetwork(currentTimeMS)
            when (networkBreedDataState) {
                DataState.Empty -> {
                    // Pass the empty response along
                    emit(networkBreedDataState)
                }
                is DataState.Success -> {
                    dbHelper.insertBreeds(networkBreedDataState.data.allItems.map { it.name })
                }
                is DataState.Error -> {
                    // Pass the error along
                    emit(networkBreedDataState)
                }

                DataState.Loading -> {
                    // Won't ever happen
                }
            }
        }

        getBreedsFromCache().conflate().collect {
            emit(it)
        }
    }

    fun getBreedsFromCache(): Flow<DataState<ItemDataSummary>> =
        dbHelper.selectAllItems()
            .map { itemList ->
                log.v { "Select all query dirtied" }
                DataState.Success(
                    ItemDataSummary(
                        itemList.maxByOrNull { it.name.length },
                        itemList
                    )
                )
            }

    private fun isBreedListStale(currentTimeMS: Long): Boolean {
        val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
        val oneHourMS = 60 * 60 * 1000
        val stale = lastDownloadTimeMS + oneHourMS < currentTimeMS
        if (!stale) {
            log.i { "Breeds not fetched from network. Recently updated" }
        }
        return stale
    }

    suspend fun getBreedsFromNetwork(currentTimeMS: Long): DataState<ItemDataSummary> {
        return try {
            val breedResult = ktorApi.getJsonFromApi()
            log.v { "Breed network result: ${breedResult.status}" }
            val breedList = breedResult.message.keys.sorted().toList()
            log.v { "Fetched ${breedList.size} breeds from network" }
            settings.putLong(DB_TIMESTAMP_KEY, currentTimeMS)
            if (breedList.isEmpty()) {
                DataState.Empty
            } else {
                DataState.Success(
                    ItemDataSummary(
                        null,
                        breedList.map { Breed(0L, it, 0L) }
                    )
                )
            }
        } catch (e: Exception) {
            log.e(e) { "Error downloading breed list" }
            DataState.Error("Unable to download breed list")
        }
    }

    suspend fun updateBreedFavorite(breed: Breed) {
        dbHelper.updateFavorite(breed.id, breed.favorite != 1L)
    }
}

data class ItemDataSummary(val longestItem: Breed?, val allItems: List<Breed>)
