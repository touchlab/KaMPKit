package co.touchlab.kampkit

import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class NativeViewModel(
    private val onSuccess: (DataState.Success<ItemDataSummary>) -> Unit,
    private val onError: (DataState.Error) -> Unit,
    private val onEmpty: (DataState.Empty) -> Unit,
    private val onLoading: () -> Unit,
) : BreedViewModel by BreedViewModelImpl() {

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
