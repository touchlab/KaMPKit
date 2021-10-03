package co.touchlab.vm

import co.touchlab.coroutines.NativeCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual val PlatformViewModel.coroutineScope: CoroutineScope
    get() = scope

actual abstract class PlatformViewModel {
    internal val scope = NativeCoroutineScope(
        context = Dispatchers.Main,
    )

    fun destroy() {
        scope.close()
        onCleared()
    }

    protected actual open fun onCleared() {
        // Do Nothing
    }
}
