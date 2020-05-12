package co.touchlab.kmp

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.touchlab.kampstarter.*
import co.touchlab.kermit.Kermit
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test


@RunWith(AndroidJUnit4::class)
@Category(CheckModuleTest::class)
class CheckModulesTest : BaseTest() {

    @Test
    fun checkAllModules() {
        initKoin{
            modules(module { single<Context> { getApplicationContext<Application>() } })
        }.checkModules {
            create<Kermit> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}