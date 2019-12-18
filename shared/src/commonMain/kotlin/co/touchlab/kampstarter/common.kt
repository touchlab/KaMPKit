package co.touchlab.kampstarter

import kotlinx.coroutines.CoroutineDispatcher

internal expect val MainDispatcher: CoroutineDispatcher

internal expect fun printThrowable(t:Throwable)