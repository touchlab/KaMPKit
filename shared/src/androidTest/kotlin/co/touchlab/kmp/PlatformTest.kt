package co.touchlab.kampstarter

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

internal actual fun <T> runTest(block: suspend CoroutineScope.() -> T) {
    runBlocking {
        block()
    }
}

/**
 * Use this rule to update the Main dispatcher ahead of tests. By delegating the main dispatcher to a new thread.
 * we can block the current thread and still dispatch main coroutines
 */
class CoroutineTestRule(
    private val testDispatcher: ExecutorCoroutineDispatcher = newSingleThreadContext("UI thread")
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@RunWith(AndroidJUnit4::class)
class SqlDelightTestJvm : SqlDelightTest() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}

@RunWith(AndroidJUnit4::class)
class BreedModelTestJvm : BreedModelTest() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}

@RunWith(AndroidJUnit4::class)
class ConcurrentJvm : ConcurrencyTest() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}