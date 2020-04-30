package co.touchlab.kmp

import co.touchlab.kampstarter.BaseTest
import co.touchlab.kampstarter.initKoin
import org.koin.core.context.stopKoin
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

class CheckModulesTest : BaseTest() {
    @Test
    fun checkAllModules() {
        initKoin{}.checkModules()
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}