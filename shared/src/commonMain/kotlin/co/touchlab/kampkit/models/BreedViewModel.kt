package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreedViewModel(
    private val breedRepository: BreedRepository,
    log: Logger
) : ViewModel() {
    private val log = log.withTag("BreedCommonViewModel")

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState(isLoading = true))

    val breedState: StateFlow<BreedViewState> = mutableBreedState

    init {
        observeBreeds()
    }

    override fun onCleared() {
        log.v("Clearing BreedViewModel")
    }

    private fun observeBreeds() {
        // Refresh breeds, and emit any exception that was thrown so we can handle it downstream
        val refreshFlow = flow<Throwable?> {
            try {
                breedRepository.refreshBreedsIfStale()
                emit(null)
            } catch (exception: Exception) {
                emit(exception)
            }
        }

        viewModelScope.launch {
            combine(refreshFlow, breedRepository.getBreeds()) { throwable, breeds -> throwable to breeds }
                .collect { (error, breeds) ->
                    mutableBreedState.update { previousState ->
                        val errorMessage = if (error != null) {
                            "Unable to download breed list"
                        } else {
                            previousState.error
                        }
                        BreedViewState(
                            isLoading = false,
                            breeds = breeds.takeIf { it.isNotEmpty() },
                            error = errorMessage.takeIf { breeds.isEmpty() },
                            isEmpty = breeds.isEmpty() && errorMessage == null
                        )
                    }
                }
        }
    }

    fun refreshBreeds(): Job {
        // Set loading state, which will be cleared when the repository re-emits
        mutableBreedState.update { it.copy(isLoading = true) }
        return viewModelScope.launch {
            log.v { "refreshBreeds" }
            try {
                breedRepository.refreshBreeds()
            } catch (exception: Exception) {
                handleBreedError(exception)
            }
        }
    }

    fun updateBreedFavorite(breed: Breed): Job {
        return viewModelScope.launch {
            breedRepository.updateBreedFavorite(breed)
        }
    }

    private fun handleBreedError(throwable: Throwable) {
        log.e(throwable) { "Error downloading breed list" }
        mutableBreedState.update {
            if (it.breeds.isNullOrEmpty()) {
                BreedViewState(error = "Unable to refresh breed list")
            } else {
                // Just let it fail silently if we have a cache
                it.copy(isLoading = false)
            }
        }
    }
}

data class BreedViewState(
    val breeds: List<Breed>? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)
