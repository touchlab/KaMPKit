package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import kotlin.test.AfterTest
import kotlin.test.Test
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.check.checkModules
import platform.Foundation.NSUserDefaults

class KoinTest {
    @Test
    fun checkAllModules() {
        initKoinIos(
            userDefaults = NSUserDefaults.standardUserDefaults,
            appInfo = TestAppInfo,
            doOnStartup = { }
        ).checkModules {
            withParameters<Logger> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
