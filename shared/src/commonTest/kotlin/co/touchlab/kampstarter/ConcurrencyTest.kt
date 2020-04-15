package co.touchlab.kampstarter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class ConcurrencyTest {
    /**
     * This doesn't test any KampKit code, but is a sanity check that our tests can handle main thread
     * coroutines without hanging
     */
    @Test
    fun testMain(){
        runTest {
            var complete = false
            val job = GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                complete = true
            }

            assertFalse(job.isCompleted)
            assertFalse(complete)

            job.join()
            assertTrue(complete)
        }
    }
}