package co.touchlab.kampkit

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.touchlab.kermit.Logger
import kotlin.test.AfterTest
import kotlin.test.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.check.checkModules
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [32])
class KoinTest {

    @Test
    fun checkAllModules() {
        initKoin(
            module {
                single<Context> { getApplicationContext<Application>() }
                single { get<Context>().getSharedPreferences("TEST", Context.MODE_PRIVATE) }
                single<AppInfo> { TestAppInfo }
                single { {} }
            },
        ).checkModules {
            withParameters<Logger> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
