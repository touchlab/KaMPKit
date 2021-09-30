package co.touchlab.kampkit

import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kampkit.viewmodel.BreedViewModelInterface
import co.touchlab.kampkit.viewmodel.SharedBreedViewModel
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BreedViewModel(
    private val onDataStateChanged: (DataState<ItemDataSummary>) -> Unit,
) : BreedViewModelInterface by SharedBreedViewModel() {

    private val mainScope = MainScope(Dispatchers.Main, log)

    init {
        ensureNeverFrozen()
        initWithScope(mainScope)
    }

    @OptIn(FlowPreview::class)
    override fun observeBreeds() {
        scope.launch {
            log.v { "Exposing flow through callbacks" }
            breedStateFlow.collect { dataState ->
                onDataStateChanged(dataState)
            }
        }
    }

    fun onDestroy() {
        mainScope.onDestroy()
    }
}
