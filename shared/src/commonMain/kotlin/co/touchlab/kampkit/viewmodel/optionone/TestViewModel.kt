package co.touchlab.kampkit.optionone

import co.touchlab.kermit.Kermit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface TestViewModelInterface : KoinComponent {

    val log: Kermit
    val scope: CoroutineScope

    fun getFlowValue(): String
    fun setFlowValue(value: String)

    val stateFlow: StateFlow<String>

    @OptIn(FlowPreview::class)
    fun observe() {
        scope.launch {
            log.v { "get: Collecting Things" }
            flowOf(
                /* TODO */
            ).flattenMerge().collect { dataState ->
                setFlowValue(dataState)
            }
        }
    }
}