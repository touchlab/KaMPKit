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
    private val onSuccess: (DataState.Success<ItemDataSummary>) -> Unit,
    private val onError: (DataState.Error) -> Unit,
    private val onEmpty: (DataState.Empty) -> Unit,
    private val onLoading: () -> Unit,
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
                when (dataState) {
                    is DataState.Success -> onSuccess(dataState)
                    is DataState.Error -> onError(dataState)
                    is DataState.Empty -> onEmpty(dataState)
                    is DataState.Loading -> onLoading()
                }
            }
        }
    }

    fun onDestroy() {
        mainScope.onDestroy()
    }
}
