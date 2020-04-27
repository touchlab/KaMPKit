package co.touchlab.kampstarter

import co.touchlab.kampstarter.models.BreedModel
import com.russhwolf.settings.MockSettings
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

abstract class CheckModulesTest : KoinTest {

    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    @BeforeTest
    fun setup() {
        appStart(dbHelper, settings, ktorApi)
    }

    @Test
    fun checkAllModules() = checkModules {
        val coreModule = module {
            single { dbHelper }
            single { settings }
            single { ktorApi }
        }
        modules(coreModule)
    }

    @AfterTest
    fun breakdown() {
        appEnd()
    }
}