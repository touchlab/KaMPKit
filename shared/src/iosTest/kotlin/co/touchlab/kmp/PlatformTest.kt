package co.touchlab.kampstarter

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest

actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }
actual fun <T> runTestWithFlow(block: suspend () -> T) { runBlockingTest { block() } }

class SqlDelightTestJvm : SqlDelightTest()

class BreedModelTestJvm: BreedModelTest()
