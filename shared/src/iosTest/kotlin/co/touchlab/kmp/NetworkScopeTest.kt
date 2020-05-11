package co.touchlab.kmp

import co.touchlab.kampstarter.BaseTest
import co.touchlab.kampstarter.ktor.network
import co.touchlab.stately.freeze
import co.touchlab.stately.isFrozen
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NetworkScopeTest : BaseTest() {
    @Test
    fun scopeIsolationTest() = runTest {
        lateinit var networkContext: CoroutineContext
        val parent = launch {
            network {
                //Fully qualified so that we are getting the current suspend func's context and not CoroutineScope.couroutineContext from the launch's receiver (which is currently "this")
                networkContext = kotlin.coroutines.coroutineContext
                delay(1000)
            }
        }

        //We have to suspend to give the child coroutines a chance to start
        delay(100)

        assertTrue(
            networkContext.isActive,
            "Scope should be active before getting cancelled"
        )

        parent.freeze()
        parent.cancel()

        assertFalse(
            networkContext.isActive,
            "Scope should be inactive after getting cancelled"
        )
        assertFalse(networkContext.isFrozen, "Scope should not have been frozen")
    }

    @Test
    fun scopeIsolationControl() = runTest {
        lateinit var controlContext: CoroutineContext
        val parent = launch {
            withContext(Dispatchers.Main) {
                controlContext = kotlin.coroutines.coroutineContext
                delay(1000)
            }
        }

        //We have to suspend to give the child coroutines a chance to start
        delay(100)

        assertTrue(
            controlContext.isActive,
            "Scope should be active before getting cancelled"
        )

        parent.freeze()
        parent.cancel()

        assertFalse(
            controlContext.isActive,
            "Scope should be inactive after getting cancelled"
        )
        assertTrue(controlContext.isFrozen, "Scope should have been frozen")
    }
}