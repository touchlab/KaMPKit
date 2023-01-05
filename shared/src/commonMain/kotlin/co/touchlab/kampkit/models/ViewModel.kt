package co.touchlab.kampkit.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

expect abstract class ViewModel<out State: ViewModelState, in Action: ViewModelAction>() {
    val viewModelScope: CoroutineScope
    abstract val state: StateFlow<State>

    protected open fun onCleared()

    abstract fun act(action: Action)
}

interface ViewModelState
interface ViewModelAction