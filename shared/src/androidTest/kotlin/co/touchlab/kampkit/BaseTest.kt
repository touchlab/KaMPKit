package co.touchlab.kampkit

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R])
actual abstract class BaseTest {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}
