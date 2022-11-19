package co.touchlab.kampkit.feature.breed

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BreedViewModel(
    private val breedRepository: BreedRepository,
    log: Logger
) : ViewModel() {

    private val log = log.withTag("BreedViewModel")

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState(isLoading = true))

    val breedState: StateFlow<BreedViewState> = mutableBreedState

    init {

        refreshIfStale()

        observeBreeds()
    }

    override fun onCleared() {
        log.v("Clearing ViewModel")
    }

    private fun refreshIfStale() {
        viewModelScope.launch {
            breedRepository.refreshDataIfStale()
        }
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            breedRepository.getData().collectLatest {
                val newState = BreedViewState(
                    breeds = it.data,
                    isError = it.isError,
                    isLoading = it.isLoading,
                    isEmpty = it.data.isEmpty()
                )
                mutableBreedState.tryEmit(newState)
            }
        }
    }

    fun refreshBreeds(): Job {
        return viewModelScope.launch {
            breedRepository.refreshData()
        }
    }

    fun updateBreedFavorite(breed: Breed): Job {
        return viewModelScope.launch {
            breedRepository.updateBreedFavorite(breed)
        }
    }

}