package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.check.checkKoinModules
import platform.Foundation.NSUserDefaults
import kotlin.test.AfterTest
import kotlin.test.Test

class KoinTest {
    @Test
    fun checkAllModules() {
        val modules = listOf(
            module {
                single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
                single { TestAppInfo }
                single { }
            },
            platformModule,
            coreModule
        )
        checkKoinModules(
            modules = modules,
            parameters = {
                withParameter<Logger> { "Test" }
                withParameter<Settings> { false }
            }
        )
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
