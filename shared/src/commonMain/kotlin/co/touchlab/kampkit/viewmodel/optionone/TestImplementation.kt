package co.touchlab.kampkit.viewmodel.optionone

import co.touchlab.kampkit.optionone.TestViewModelInterface
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class TestImplementation(
    override val log: Kermit,
    override val scope: CoroutineScope
) : TestViewModelInterface {

    //override val scope = throw NotImplementedError("Not Implemented in Parent")
    //override val log: Kermit by inject { parametersOf("TestImplementation") }
    private val _stateFlow: MutableStateFlow<String> =  MutableStateFlow("")

    override fun getFlowValue(): String = _stateFlow.value
    override fun setFlowValue(value: String) {
        _stateFlow.value = value
    }

    override val stateFlow: StateFlow<String>
        get() = _stateFlow

    init {
        observe()
    }
}