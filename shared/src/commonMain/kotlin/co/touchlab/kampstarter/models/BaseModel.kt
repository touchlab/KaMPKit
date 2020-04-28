package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.printThrowable
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseModel : KoinComponent {
    internal val scope = MainScope(Dispatchers.Main)
    private val ktorContext: CoroutineContext by lazy { scope.childContext() }


    open fun onDestroy() {
        scope.job.cancel()
    }

    suspend fun <R> network(block: suspend () -> R): R {
        if(!isMainThread)
            throw IllegalStateException("Ktor calls must be run in main thread")
        return withContext(ktorContext) { block() }
    }
}

internal class MainScope(internal val mainContext: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = mainContext + job + exceptionHandler

    internal val job = SupervisorJob()
    internal val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showError(throwable)
    }

    //TODO: Some way of exposing this to the caller without trapping a reference and freezing it.
    internal fun showError(t: Throwable) {
        printThrowable(t)
    }
}

internal expect fun CoroutineScope.childContext(): CoroutineContext
internal expect val isMainThread:Boolean
