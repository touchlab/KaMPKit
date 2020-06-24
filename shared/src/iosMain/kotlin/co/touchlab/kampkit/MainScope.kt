package co.touchlab.kampkit

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import co.touchlab.kermit.Kermit

class MainScope(private val mainContext: CoroutineContext, private val log:Kermit) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = mainContext + job + exceptionHandler

    internal val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        printThrowable(throwable)
        showError(throwable)
    }

    //TODO: Some way of exposing this to the caller without trapping a reference and freezing it.
    private fun showError(t: Throwable) {
        log.e(throwable = t) { "Error in MainScope" }
    }

    fun onDestroy(){
        job.cancel()
    }
}
