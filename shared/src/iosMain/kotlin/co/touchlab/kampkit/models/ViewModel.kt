package co.touchlab.kampkit.models

import co.touchlab.kampkit.FlowAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clear] at the appropriate time.
 */
actual abstract class ViewModel {

    actual val viewModelScope = MainScope()

    /**
     * Override this to do any cleanup immediately before the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]
     * is cancelled in [clear]
     */
    protected actual open fun onCleared() {
    }

    /**
     * Cancels the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]. After this is called, the ViewModel should
     * no longer be used.
     */
    fun clear() {
        onCleared()
        viewModelScope.cancel()
    }
}

abstract class CallbackViewModel {
    protected abstract val viewModel: ViewModel

    /**
     * Create a [FlowAdapter] from this [Flow] to make it easier to interact with from Swift.
     */
    fun <T : Any> Flow<T>.asCallbacks() =
        FlowAdapter(viewModel.viewModelScope, this)

    @Suppress("Unused") // Called from Swift
    fun clear() = viewModel.clear()
}
