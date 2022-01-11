package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.plus

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clear] at the appropriate time.
 */
abstract class CallbackViewModel(log: Logger) {

    val viewModelScope = MainScope() + kermitExceptionHandler(log)

    /**
     * Override this to do any cleanup immediately before the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]
     * is cancelled in [clear]
     */
    protected open fun onClear() {
    }

    /**
     * Cancels the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]. After this is called, the ViewModel should
     * no longer be used.
     */
    @Suppress("Unused") // Called from Swift
    fun clear() {
        onClear()
        viewModelScope.cancel()
    }

    /**
     * Create a [FlowAdapter] from this [Flow] to make it easier to interact with from Swift.
     */
    fun <T : Any> Flow<T>.asCallbacks() =
        FlowAdapter(viewModelScope, this)
}
