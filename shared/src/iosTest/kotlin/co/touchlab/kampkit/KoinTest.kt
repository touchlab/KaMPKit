package co.touchlab.kampkit

import co.touchlab.kermit.Kermit
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.check.checkModules
import platform.Foundation.NSUserDefaults
import kotlin.test.AfterTest
import kotlin.test.Test

class KoinTest : BaseTest() {
    @Test
    fun checkAllModules() {
        initKoinIos(
            userDefaults = NSUserDefaults.standardUserDefaults,
            appInfo = TestAppInfo,
            doOnStartup = { }
        ).checkModules {
            create<Kermit> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
