package co.touchlab.vm

import kotlinx.coroutines.isActive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlatformViewModelTest {

    @Test
    fun destroy_closeNativeCoroutineScope() {
        val viewModel = StubPlatformViewModel()

        viewModel.destroy()

        assertFalse(viewModel.coroutineScope.isActive)
    }

    @Test
    fun destroy_callOnCleared() {
        val viewModel = StubPlatformViewModel()

        viewModel.destroy()

        assertTrue(viewModel.onClearedCalled)
    }

    private class StubPlatformViewModel : PlatformViewModel() {
        var onClearedCalled = false

        override fun onCleared() {
            onClearedCalled = true
        }
    }
}
