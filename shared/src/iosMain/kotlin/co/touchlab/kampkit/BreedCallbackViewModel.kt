package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedCommonViewModel
import co.touchlab.kampkit.models.BreedRepository
import co.touchlab.kermit.Logger

@Suppress("Unused") // Called from Swift
class BreedCallbackViewModel(
    breedRepository: BreedRepository,
    log: Logger
) : CallbackViewModel(log) {

    private val commonViewModel = BreedCommonViewModel(breedRepository, log, viewModelScope)

    val breeds = commonViewModel.breeds.asCallbacks()

    fun refreshBreeds() = commonViewModel.refreshBreeds()

    fun updateBreedFavorite(breed: Breed) = commonViewModel.updateBreedFavorite(breed)
}
