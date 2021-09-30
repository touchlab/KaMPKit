package co.touchlab.kampkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class AndroidBreedViewModel : ViewModel(), BreedViewModel {

    override val log: Kermit by inject { parametersOf("BreedViewModel") }
    override val scope = viewModelScope
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
        observeBreeds()
    }
}
