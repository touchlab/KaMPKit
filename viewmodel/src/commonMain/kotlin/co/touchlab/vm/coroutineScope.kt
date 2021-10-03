package co.touchlab.vm

import kotlinx.coroutines.CoroutineScope

expect val PlatformViewModel.coroutineScope: CoroutineScope

expect abstract class PlatformViewModel() {
    protected open fun onCleared()
}
