package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.printThrowable
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

open class BaseModel : KoinComponent {
    private val log: Kermit by inject { parametersOf("BaseModel") }
    internal val scope = MainScope(Dispatchers.Main, log.withTag("MainScope"))

    open fun onDestroy() {
        log.v { "onDestroy called" }
        scope.job.cancel()
    }
}

internal class MainScope(private val mainContext: CoroutineContext, private val log:Kermit) : CoroutineScope {
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
}
