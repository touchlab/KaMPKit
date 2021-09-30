package co.touchlab.kampkit.viewmodel

import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

open class SharedBreedViewModel : BreedViewModelInterface {

    override lateinit var scope: CoroutineScope
    override val log: Kermit by inject { parametersOf("BreedViewModel") }
    override val breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> =
        MutableStateFlow(DataState())

    override fun getFlowValue(): DataState<ItemDataSummary> = _breedStateFlow.value

    override fun setFlowValue(value: DataState<ItemDataSummary>) {
        _breedStateFlow.value = value
    }

    override val breedStateFlow: StateFlow<DataState<ItemDataSummary>>
        get() = _breedStateFlow
}
