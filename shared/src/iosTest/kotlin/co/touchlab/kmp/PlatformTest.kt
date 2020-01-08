package co.touchlab.kampstarter

import kotlinx.coroutines.runBlocking

actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }

class SqlDelightTestJvm : SqlDelightTest()

class BreedModelTestJvm: BreedModelTest()