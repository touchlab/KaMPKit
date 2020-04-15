package co.touchlab.kampstarter

import kotlinx.coroutines.*
import platform.CoreFoundation.CFRunLoopGetCurrent
import platform.CoreFoundation.CFRunLoopRun
import platform.CoreFoundation.CFRunLoopStop

internal actual fun <T> runTest(block: suspend CoroutineScope.() -> T) {
    GlobalScope.launch(Dispatchers.Main) {
        try {
            block()
        }
        finally {
            CFRunLoopStop(CFRunLoopGetCurrent())
        }
    }
    CFRunLoopRun()
}

class SqlDelightTestIos : SqlDelightTest()
class BreedModelTestIos : BreedModelTest()
class ConcurrentIos:ConcurrencyTest()