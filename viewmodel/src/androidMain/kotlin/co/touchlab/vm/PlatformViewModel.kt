package co.touchlab.vm

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual val PlatformViewModel.coroutineScope: CoroutineScope
    get() = viewModelScope

actual abstract class PlatformViewModel : ViewModel() {
    @CallSuper
    protected actual override fun onCleared() {
        super.onCleared()
    }
}
