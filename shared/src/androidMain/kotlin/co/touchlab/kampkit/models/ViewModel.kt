package co.touchlab.kampkit.models

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.viewModelScope as androidXViewModelScope

actual abstract class ViewModel actual constructor() : AndroidXViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}
