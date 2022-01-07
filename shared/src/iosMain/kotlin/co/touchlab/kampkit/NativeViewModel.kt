package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.DogApi
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Logger
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NativeViewModel(
    private val onDataState: (DataState<ItemDataSummary>) -> Unit
) : KoinComponent {

    private val log: Logger by injectLogger("BreedModel")
    private val scope = MainScope(Dispatchers.Main, log)

    private val dbHelper: DatabaseHelper by inject()
    private val settings: Settings by inject()
    private val dogApi: DogApi by inject()
    private val clock: Clock by inject()
    private val breedModel: BreedModel = BreedModel(dbHelper, settings, dogApi, log, clock)

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
