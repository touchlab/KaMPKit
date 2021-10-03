package co.touchlab.coroutines

fun interface NativeCoroutineErrorLogger {
    fun log(error: Throwable)
}
