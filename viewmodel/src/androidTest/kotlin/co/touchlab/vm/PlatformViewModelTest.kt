package co.touchlab.vm

import androidx.lifecycle.ViewModel
import kotlin.test.Test
import kotlin.test.assertIs

class PlatformViewModelTest {

    @Test
    fun actualClass_isLifecycleViewModelChild() {
        val viewModel = object : PlatformViewModel() {}
        assertIs<ViewModel>(viewModel)
    }
}
