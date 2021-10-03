package co.touchlab.kampkit.vm

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.vm.PlatformViewModel
import co.touchlab.vm.coroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

abstract class CommonBreedViewModel : PlatformViewModel(), KoinComponent {

    // === Dependencies ===
    //
    // Problem:
    // -> If I add those properties to the constructor, I can't inject them on iOS
    // -> If they are abstract and each platform provides on it's own way, the Android ViewModel crashes
    //
    // Solution: This class inherits from KoinComponent and I can inject the parameters directly here
    //
    // TODO: PoC of iOS ViewModelFactory (this way we can move dependencies to constructor)
    protected val log: Kermit by inject { parametersOf("BreedModel") }
    private val breedModel: BreedModel = BreedModel()

    // All state that will be public to the platforms have to be protected
    // This way, each platform view model can expose as they can handle.
    //
    // If for some reason the way some platform expose the data changes, the common class
    // and logic stays the same.
    //
    // See child implementations for more details
    protected val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> =
        MutableStateFlow(DataState(loading = true))

    init {
        observeBreeds()
    }

    @OptIn(FlowPreview::class)
    private fun observeBreeds() {
        coroutineScope.launch {
            log.v { "getBreeds: Collecting Things" }
            flowOf(
                breedModel.refreshBreedsIfStale(true),
                breedModel.getBreedsFromCache()
            ).flattenMerge().collect { dataState ->
                if (dataState.loading) {
                    val temp = _breedStateFlow.value.copy(loading = true)
                    _breedStateFlow.value = temp
                } else {
                    _breedStateFlow.value = dataState
                }
            }
        }
    }

    fun refreshBreeds(forced: Boolean = false) {
        coroutineScope.launch {
            log.v { "refreshBreeds" }
            breedModel.refreshBreedsIfStale(forced).collect { dataState ->
                if (dataState.loading) {
                    val temp = _breedStateFlow.value.copy(loading = true)
                    _breedStateFlow.value = temp
                } else {
                    _breedStateFlow.value = dataState
                }
            }
        }
    }

    fun updateBreedFavorite(breed: Breed) {
        coroutineScope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }
}
