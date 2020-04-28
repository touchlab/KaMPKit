package co.touchlab.kampstarter.models

import android.os.Looper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

internal actual fun CoroutineScope.childContext(): CoroutineContext =
    coroutineContext + Job(parent = coroutineContext[Job]) + coroutineContext[CoroutineExceptionHandler]!!

internal actual val isMainThread: Boolean
    get() = Looper.getMainLooper() === Looper.myLooper()