package co.touchlab.kampkit.models

import co.touchlab.kampkit.DatabaseHelper
import co.touchlab.kampkit.base.ApiStatus
import co.touchlab.kampkit.base.StaleDataDelegate
import co.touchlab.kampkit.base.StaleDataKey
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.Api
import co.touchlab.kampkit.response.BreedResult
import co.touchlab.kermit.Logger
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class BreedRepositoryImpl(
    private val log: Logger,
    private val dbHelper: DatabaseHelper,
    private val api: Api,
    private val staleDataDelegate: StaleDataDelegate
): BreedRepository {

    init {
        ensureNeverFrozen()
    }

    private val requestStatus = MutableStateFlow<ApiStatus<BreedResult>>(ApiStatus.NoAction)

    override fun getData(): Flow<BreedRepoData> =
        combine(dbHelper.selectAllItems(), requestStatus.asStateFlow()) { items, requestStatus ->
            when(requestStatus) {
                is ApiStatus.Error -> {
                    BreedRepoData(isError = true)
                }
                ApiStatus.NoAction,
                ApiStatus.NetworkError,
                is ApiStatus.Success -> {
                    BreedRepoData(
                        data = items.sortedBy { it.name }
                    )
                }
                ApiStatus.Loading -> BreedRepoData(isLoading = true)
            }
        }

    override suspend fun refreshDataIfStale() {
        log.d { "Checking if data is stale." }
        if (staleDataDelegate.isDataStale(StaleDataKey.BreedStaleData)) {
            refreshData()
        }
    }

    override suspend fun refreshData() {
        requestStatus.tryEmit(ApiStatus.Loading)

        log.d { "Refreshing data." }

        val result = api.getBreeds()
        staleDataDelegate.updateLastTime(StaleDataKey.BreedStaleData)

        val data = result as? ApiStatus.Success
        data?.let {
            persistData(it.data)
        }

        log.d { "Network result: $result" }

        requestStatus.tryEmit(result)
    }

    private suspend fun persistData(data: BreedResult) {
        dbHelper.insertBreeds(data.message.keys.toList())
    }

    override suspend fun updateBreedFavorite(breed: Breed) {
        dbHelper.updateFavorite(breed.id, !breed.favorite)
    }

}
