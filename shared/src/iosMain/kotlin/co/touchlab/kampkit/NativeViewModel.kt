package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class NativeViewModel(
    private val onDataState: (DataState<ItemDataSummary>) -> Unit
) : KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedModel") }
    private val scope = MainScope(Dispatchers.Main, log)
    private val breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState(loading = true)
    )

    init {
        ensureNeverFrozen()
        observeBreeds()
    }

    fun consumeError() {
        _breedStateFlow.value = _breedStateFlow.value.copy(exception = null)
    }

    @OptIn(FlowPreview::class)
    fun observeBreeds() {
        scope.launch {
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

        scope.launch {
            log.v { "Exposing flow through callbacks" }
            _breedStateFlow.collect { dataState ->
                onDataState(dataState)
            }
        }
    }

    fun refreshBreeds(forced: Boolean = false) {
        scope.launch {
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
        scope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }

    fun onDestroy() {
        scope.onDestroy()
    }
}
