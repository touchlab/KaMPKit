package co.touchlab.kampkit.vm

import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.vm.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun BreedViewModel.observeBreedChanges(
    block: (DataState<ItemDataSummary>) -> Unit
) {
    coroutineScope.launch {
        breedStateFlow.collect { block(it) }
    }
}
