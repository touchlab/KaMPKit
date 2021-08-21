package co.touchlab.kampkit

import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
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
) : BreedViewModel {

    override val log: Kermit by inject { parametersOf("BreedModel") }
    override val scope = MainScope(Dispatchers.Main, log)
    override val breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> =
        MutableStateFlow(DataState.Loading)

    override fun getFlowValue(): DataState<ItemDataSummary> = _breedStateFlow.value

    override fun setFlowValue(value: DataState<ItemDataSummary>) {
        _breedStateFlow.value = value
    }

    override val breedStateFlow: StateFlow<DataState<ItemDataSummary>>
        get() = _breedStateFlow

    init {
        ensureNeverFrozen()
        observeBreeds()
    }

    @OptIn(FlowPreview::class)
    override fun observeBreeds() {
        super.observeBreeds()

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
        scope.onDestroy()
    }
}
