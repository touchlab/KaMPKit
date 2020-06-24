package co.touchlab.kampkit

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import co.touchlab.stately.freeze
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

class CheckModulesTest : BaseTest() {
    @Test
    fun checkAllModules() {
        initKoin {}.checkModules{
            create<Kermit> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}