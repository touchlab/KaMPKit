package co.touchlab.kampstarter

import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConcurrencyTest:BaseTest() {
    /**
     * This doesn't test any KampKit code, but is a sanity check that our tests can handle main thread
     * coroutines without hanging
     */
    @Test
    fun testMain() {
        runTest {
            withTimeout(2000) {
                var complete = false
                val job = GlobalScope.launch(Dispatchers.Main) {
                    delay(300)
                    complete = true
                }

                assertFalse(job.isCompleted)
                assertFalse(complete)

                job.join()
                assertTrue(complete)
            }
        }
    }
}