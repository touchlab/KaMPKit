package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed
import co.touchlab.kermit.Logger
import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class BreedViewModel(
    private val breedRepository: BreedRepository,
    private val log: Logger
) : ViewModel() {

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState.Initial)

    val breedState: StateFlow<BreedViewState> = mutableBreedState.asStateFlow()

    /**
     * Activates this viewModel so that `breedState` returns the current breed state. Suspends until cancelled, at
     * which point `breedState` will no longer update.
     */
    suspend fun activate() {
        observeBreeds()
    }

    override fun onCleared() {
        log.v("Clearing BreedViewModel")
    }

    private suspend fun observeBreeds() {
        // Refresh breeds, and emit any exception that was thrown so we can handle it downstream
        val refreshFlow = flow<Throwable?> {
            try {
                breedRepository.refreshBreedsIfStale()
                emit(null)
            } catch (exception: Exception) {
                emit(exception)
            }
        }

        combine(
            refreshFlow,
            breedRepository.getBreeds()
        ) { throwable, breeds -> throwable to breeds }
            .collect { (error, breeds) ->
                mutableBreedState.update { previousState ->
                    val errorMessage = if (error != null) {
                        "Unable to download breed list"
                    } else if (previousState is BreedViewState.Error) {
                        previousState.error
                    } else {
                        null
                    }

                    if (breeds.isNotEmpty()) {
                        BreedViewState.Content(breeds)
                    } else if (errorMessage != null) {
                        BreedViewState.Error(errorMessage)
                    } else {
                        BreedViewState.Empty()
                    }
                }
            }
    }

    suspend fun refreshBreeds() {
        // Set loading state, which will be cleared when the repository re-emits
        mutableBreedState.update {
            when (it) {
                is BreedViewState.Initial -> it
                is BreedViewState.Content -> it.copy(isLoading = true)
                is BreedViewState.Empty -> it.copy(isLoading = true)
                is BreedViewState.Error -> it.copy(isLoading = true)
            }
        }

        log.v { "refreshBreeds" }
        try {
            breedRepository.refreshBreeds()
        } catch (exception: Exception) {
            handleBreedError(exception)
        }
    }

    suspend fun updateBreedFavorite(breed: Breed) {
        breedRepository.updateBreedFavorite(breed)
    }

    private fun handleBreedError(throwable: Throwable) {
        log.e(throwable) { "Error downloading breed list" }
        mutableBreedState.update {
            when (it) {
                is BreedViewState.Content -> it.copy(
                    isLoading = false
                ) // Just let it fail silently if we have a cache
                is BreedViewState.Empty,
                is BreedViewState.Error,
                is BreedViewState.Initial -> BreedViewState.Error(
                    error = "Unable to refresh breed list"
                )
            }
        }
    }
}

sealed class BreedViewState {
    abstract val isLoading: Boolean

    data object Initial : BreedViewState() {
        override val isLoading: Boolean = true
    }

    data class Empty @DefaultArgumentInterop.Enabled constructor(
        override val isLoading: Boolean = false
    ) : BreedViewState()

    data class Content @DefaultArgumentInterop.Enabled constructor(
        val breeds: List<Breed>,
        override val isLoading: Boolean = false
    ) : BreedViewState()

    data class Error @DefaultArgumentInterop.Enabled constructor(
        val error: String,
        override val isLoading: Boolean = false
    ) : BreedViewState()
}
