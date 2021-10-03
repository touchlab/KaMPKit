package co.touchlab.kampkit.vm

import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.vm.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.native.concurrent.ensureNeverFrozen

class BreedViewModel(
    private val onDataState: (DataState<ItemDataSummary>) -> Unit,
) : CommonBreedViewModel() {
    init {
        observeBreeds()
        ensureNeverFrozen()
    }

    private fun observeBreeds() {
        coroutineScope.launch {
            log.v { "Exposing flow through callbacks" }

            // On iOS we expose this value using a callback
            _breedStateFlow.collect { dataState ->
                onDataState(dataState)
            }
        }
    }
}
