package co.touchlab.kampstarter.models

import android.os.Looper
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

internal actual fun MainScope.childContext(): CoroutineContext =
    mainContext + Job(parent = job) + exceptionHandler

internal actual val isMainThread: Boolean
    get() = Looper.getMainLooper() === Looper.myLooper()