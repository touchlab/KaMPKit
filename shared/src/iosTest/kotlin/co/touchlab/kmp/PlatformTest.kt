package co.touchlab.kampstarter

import kotlinx.coroutines.runBlocking

internal actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }

internal class SqlDelightTestJvm : SqlDelightTest()

internal class BreedModelTestJvm: BreedModelTest()