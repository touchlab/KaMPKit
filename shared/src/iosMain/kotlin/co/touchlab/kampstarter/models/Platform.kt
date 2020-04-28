package co.touchlab.kampstarter.models

import co.touchlab.stately.concurrency.GuardedStableRef
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import platform.Foundation.NSThread
import kotlin.coroutines.CoroutineContext

@OptIn(InternalCoroutinesApi::class)
internal actual fun MainScope.childContext(): CoroutineContext {
    val ktorJob = Job()

    val ref = GuardedStableRef(ktorJob)

    job.invokeOnCompletion(onCancelling = true) {cancelCause ->
        val parentJob = ref.state

        if(cancelCause is CancellationException)
            parentJob.cancel(cause = cancelCause)
        else
            parentJob.cancel()
    }

    return mainContext + Job() + exceptionHandler
}

internal actual val isMainThread: Boolean
    get() = NSThread.isMainThread