package com.touchlab.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun platformName(): String {
    return "Android"
}

internal actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main