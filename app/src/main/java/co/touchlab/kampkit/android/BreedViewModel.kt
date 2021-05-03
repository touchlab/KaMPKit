package co.touchlab.kampkit.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class BreedViewModel : ViewModel(), KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedViewModel") }
    private val scope = viewModelScope
    private val breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState.Loading
    )

    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow

    init {
        observeBreeds()
    }

    @OptIn(FlowPreview::class)
    private fun observeBreeds() {
        scope.launch {
            log.v { "getBreeds: Collecting Things" }
            flowOf(
                breedModel.refreshBreedsIfStale(true),
                breedModel.getBreedsFromCache()
            ).flattenMerge().collect { dataState ->
                _breedStateFlow.value = dataState
            }
        }
    }

    fun refreshBreeds(forced: Boolean = false) {
        scope.launch {
            log.v { "refreshBreeds" }
            breedModel.refreshBreedsIfStale(forced).collect {
                _breedStateFlow.value = it
            }
        }
    }

    fun updateBreedFavorite(breed: Breed) {
        scope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }
}
