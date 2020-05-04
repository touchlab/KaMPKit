package co.touchlab.kampstarter.ktor

internal actual suspend fun <R> network(block: suspend () -> R): R = block()
