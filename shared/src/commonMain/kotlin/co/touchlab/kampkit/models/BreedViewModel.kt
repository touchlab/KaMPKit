package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreedViewModel(
    private val breedRepository: BreedRepository,
    log: Logger
) : ViewModel<BreedViewState, BreedViewAction>() {
    private val log = log.withTag("BreedCommonViewModel")

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState.Loading())

    override val state: StateFlow<BreedViewState> = mutableBreedState

    init {
        observeBreeds()
    }

    override fun onCleared() {
        log.v("Clearing BreedViewModel")
    }

    override fun act(action: BreedViewAction) {
        when (action) {
            BreedViewAction.RefreshBreeds -> refreshBreeds()
            is BreedViewAction.UpdateBreedFavorite -> updateBreedFavorite(action.breed)
        }
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
                .catch {
                    mutableBreedState.update { previousState ->
                        BreedViewState.Error(it, previousState.breeds)
                    }
                }
                .collect { (error, breeds) ->
                    mutableBreedState.update { previousState ->
                        if (error != null) {
                            BreedViewState.Error(error, previousState.breeds)
                        } else {
                            BreedViewState.Success(breeds)
                        }
                    }
                }
        }
    }

    private fun refreshBreeds(): Job {
        // Set loading state, which will be cleared when the repository re-emits
        mutableBreedState.update { BreedViewState.Loading(breeds = it.breeds) }
        return viewModelScope.launch {
            log.v { "refreshBreeds" }
            try {
                breedRepository.refreshBreeds()
            } catch (exception: Exception) {
                handleBreedError(exception)
            }
        }
    }

    private fun updateBreedFavorite(breed: Breed): Job {
        return viewModelScope.launch {
            breedRepository.updateBreedFavorite(breed)
        }
    }

    private fun handleBreedError(throwable: Throwable) {
        log.e(throwable) { "Error downloading breed list" }
        mutableBreedState.update {
            BreedViewState.Error(throwable, (it as? BreedViewState.Success)?.breeds)
        }
    }
}

sealed class BreedViewState(open val breeds: List<Breed>?): ViewModelState {
    data class Loading(override val breeds: List<Breed>? = null): BreedViewState(breeds)
    data class Error(val throwable: Throwable, override val breeds: List<Breed>? = null): BreedViewState(breeds)
    data class Success(override val breeds: List<Breed>): BreedViewState(breeds)
}

sealed class BreedViewAction: ViewModelAction {
    object RefreshBreeds: BreedViewAction()
    data class UpdateBreedFavorite(val breed: Breed): BreedViewAction()
}