package co.touchlab.kampstarter

import kotlinx.coroutines.runBlocking

internal actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }

class SqlDelightTestIos : SqlDelightTest()

class BreedModelTestIos : BreedModelTest()