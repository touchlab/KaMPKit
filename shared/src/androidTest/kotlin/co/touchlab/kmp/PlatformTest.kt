package co.touchlab.kampstarter

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith

internal actual fun <T> runTest(block: suspend () -> T) { runBlocking { block() } }

@RunWith(AndroidJUnit4::class)
internal class SqlDelightTestJvm : SqlDelightTest()

@RunWith(AndroidJUnit4::class)
internal class BreedModelTestJvm: BreedModelTest()