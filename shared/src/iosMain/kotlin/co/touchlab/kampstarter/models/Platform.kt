package co.touchlab.kampstarter.models

import co.touchlab.stately.concurrency.GuardedStableRef
import kotlinx.coroutines.*
import platform.Foundation.NSThread
import kotlin.coroutines.CoroutineContext

@OptIn(InternalCoroutinesApi::class)
internal actual fun CoroutineScope.childContext(): CoroutineContext {
    val ktorJob = Job()

    val ref = GuardedStableRef(ktorJob)

    coroutineContext[Job]!!.invokeOnCompletion(onCancelling = true) { cancelCause ->
        val parentJob = ref.state

        if (cancelCause is CancellationException)
            parentJob.cancel(cause = cancelCause)
        else
            parentJob.cancel()
    }

    return coroutineContext + Job() + coroutineContext[CoroutineExceptionHandler]!!
}

internal actual val isMainThread: Boolean
    get() = NSThread.isMainThread