package co.touchlab.kampkit.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.viewModelScope as androidXViewModelScope

actual abstract class ViewModel<out State: ViewModelState, in Action: ViewModelAction> actual constructor() : AndroidXViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }

    actual abstract fun act(action: Action)
    actual abstract val state: StateFlow<State>
}
