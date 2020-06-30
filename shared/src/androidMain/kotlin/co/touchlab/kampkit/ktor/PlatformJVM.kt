package co.touchlab.kampkit.ktor

internal actual suspend fun <R> network(block: suspend () -> R): R = block()
