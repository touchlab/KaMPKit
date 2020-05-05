package co.touchlab.kmp

import co.touchlab.kampstarter.BaseTest
import co.touchlab.kampstarter.ktor.network
import co.touchlab.kampstarter.models.BaseModel
import co.touchlab.stately.freeze
import co.touchlab.stately.isFrozen
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BaseModelTest : BaseTest() {
    @Test
    fun testScopeIsolation() = runTest {
        lateinit var networkContext: CoroutineContext
        lateinit var controlContext: CoroutineContext
        val model = BaseModel()

        //Control: the child context should get frozen when we freeze the parent
        model.scope.launch {
            withContext(Dispatchers.Main) {
                controlContext = kotlin.coroutines.coroutineContext
                delay(1000)
            }
        }

        //Test: child context shouldn't be frozen
        model.scope.launch {
            network {
                //Fully qualified so that we are getting the current suspend func's context and not CoroutineScope.couroutineContext from the launch's receiver (which is currently "this")
                networkContext = kotlin.coroutines.coroutineContext
                delay(1000)
            }
        }

        //We have to suspend to give the child coroutines a chance to start
        delay(100)

        assertTrue(networkContext.isActive, "network scope should be active before getting cancelled")

        model.scope.freeze()
        model.scope.cancel()

        assertFalse(networkContext.isActive, "Network scope should be inactive after getting cancelled")
        assertFalse(networkContext.isFrozen, "Network scope should not have been frozen")
        assertTrue(controlContext.isFrozen, "Control scope should have been frozen")
    }
}