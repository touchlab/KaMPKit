package co.touchlab.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class NativeCoroutineScope(
    private val context: CoroutineContext,
) : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = context + exceptionHandler + job

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        print("NativeCoroutineScope: ${throwable.stackTraceToString()}")
    }

    fun close() {
        coroutineContext.cancel()
    }
}
