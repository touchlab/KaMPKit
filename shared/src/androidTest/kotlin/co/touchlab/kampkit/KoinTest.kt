package co.touchlab.kampkit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.touchlab.kermit.LoggerConfig
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.category.CheckModuleTest
import org.koin.test.verify.verify
import org.robolectric.annotation.Config
import kotlin.test.AfterTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@Category(CheckModuleTest::class)
@Config(sdk = [32])
class KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        val testModule = module {
            single<Context> { getApplicationContext<Application>() }
            single<SharedPreferences> {
                get<Context>().getSharedPreferences(
                    "TEST",
                    Context.MODE_PRIVATE
                )
            }
            single<AppInfo> { TestAppInfo }
            single { {} }

            includes(
                platformModule,
                coreModule
            )
        }
        testModule.verify(
            extraTypes = listOf(
                Boolean::class,
                LoggerConfig::class,
                String::class,
                CoroutineDispatcher::class
            )
        )
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
