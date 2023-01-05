package co.touchlab.kampkit.models

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clear] at the appropriate time.
 */
actual abstract class ViewModel<out State: ViewModelState, in Action: ViewModelAction> {

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
        viewModelScope.cancel()
        onCleared()
    }

    actual abstract val state: StateFlow<State>

    @Suppress("unused")
    actual abstract fun act(action: Action)

    @Suppress("unused")
    inline fun subscribe(
        crossinline onEach: (item: State) -> Unit,
        crossinline onComplete: () -> Unit,
        crossinline onThrow: (error: Throwable) -> Unit
    ) = state.onEach { onEach(it) }
        .catch { onThrow(it) }
        .onCompletion { onComplete() }
        .launchIn(viewModelScope)
}
