package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class BreedCommonViewModel(
    private val breedRepository: BreedRepository,
    log: Logger,
    private val scope: CoroutineScope
) {
    private val log = log.withTag("BreedCommonViewModel")

    private val mutableBreeds: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState(loading = true)
    )

    val breeds: StateFlow<DataState<ItemDataSummary>> = mutableBreeds

    init {
        observeBreeds()
    }

    @OptIn(FlowPreview::class)
    private fun observeBreeds() {
        scope.launch {
            log.v { "getBreeds: Collecting Things" }
            flowOf(
                breedRepository.refreshBreedsIfStale(true),
                breedRepository.getBreedsFromCache()
            ).flattenMerge().collect { dataState ->
                if (dataState.loading) {
                    mutableBreeds.value = mutableBreeds.value.copy(loading = true)
                } else {
                    mutableBreeds.value = dataState
                }
            }
        }
    }

    fun refreshBreeds() {
        scope.launch {
            log.v { "refreshBreeds" }
            breedRepository.refreshBreedsIfStale(true).collect { dataState ->
                if (dataState.loading) {
                    mutableBreeds.value = mutableBreeds.value.copy(loading = true)
                } else {
                    mutableBreeds.value = dataState
                }
            }
        }
    }

    fun updateBreedFavorite(breed: Breed) {
        scope.launch {
            breedRepository.updateBreedFavorite(breed)
        }
    }
}
