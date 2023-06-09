package co.touchlab.kampkit.ui.breeds

import co.touchlab.kampkit.data.dog.DogRepository
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.core.ViewModel
import co.touchlab.kermit.Logger
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("BreedViewModelDelegate")
class BreedsViewModel(
    private val dogRepository: DogRepository,
    log: Logger
) : ViewModel() {
    private val log = log.withTag("BreedViewModel")

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState(isLoading = true))

    @NativeCoroutinesState
    val breedsState: StateFlow<BreedViewState> = mutableBreedState

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
                dogRepository.refreshBreedsIfStale()
                emit(null)
            } catch (exception: Exception) {
                emit(exception)
            }
        }

        viewModelScope.launch {
            combine(refreshFlow, dogRepository.getBreeds()) { throwable, breeds -> throwable to breeds }
                .collect { (error, breeds) ->
                    mutableBreedState.update { previousState ->
                        val errorMessage = if (error != null) {
                            "Unable to download breed list"
                        } else {
                            previousState.error
                        }
                        BreedViewState(
                            isLoading = false,
                            breeds = breeds,
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
                dogRepository.refreshBreeds()
            } catch (exception: Exception) {
                handleBreedError(exception)
            }
        }
    }

    fun updateBreedFavorite(breed: Breed): Job {
        return viewModelScope.launch {
            dogRepository.updateBreedFavorite(breed)
        }
    }

    private fun handleBreedError(throwable: Throwable) {
        log.e(throwable) { "Error downloading breed list" }
        mutableBreedState.update {
            if (it.breeds.isEmpty()) {
                BreedViewState(error = "Unable to refresh breed list")
            } else {
                // Just let it fail silently if we have a cache
                it.copy(isLoading = false)
            }
        }
    }
}

data class BreedViewState(
    val breeds: List<Breed> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
) {
    companion object {
        // This method lets you use the default constructor values in Swift. When accessing the
        // constructor directly, they will not work there and would need to be provided explicitly.
        fun default() = BreedViewState()
    }
}
