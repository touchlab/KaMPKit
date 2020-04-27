package co.touchlab.kmp

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.touchlab.kampstarter.*
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@Category(CheckModuleTest::class)
class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() = checkModules {
        initKoin {}
        modules(coreModule, platformModule)
    }
}