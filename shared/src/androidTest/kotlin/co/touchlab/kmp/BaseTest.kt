package co.touchlab.kampstarter

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.test.category.CheckModuleTest

@RunWith(AndroidJUnit4::class)
actual abstract class BaseTest {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    actual fun <T> runTest(block: suspend CoroutineScope.() -> T) {
        runBlocking { block() }
    }
}


@RunWith(AndroidJUnit4::class)
@Category(CheckModuleTest::class)
class CheckModulesTestJvm : CheckModulesTest()
