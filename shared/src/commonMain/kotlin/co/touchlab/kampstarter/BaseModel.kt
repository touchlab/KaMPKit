package co.touchlab.kampstarter

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseModel {
    internal val mainScope = MainScope(MainDispatcher)

    open fun onDestroy() {
        mainScope.job.cancel()
    }
}

internal class MainScope(private val mainContext: CoroutineContext): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = mainContext + job + exceptionHandler

    internal val job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showError(throwable)
    }

    //TODO: Some way of exposing this to the caller without trapping a reference and freezing it.
    fun showError(t: Throwable) {
        printThrowable(t)
    }
}