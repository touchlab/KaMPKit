package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import co.touchlab.kampkit.ui.MainScreen
import co.touchlab.kampkit.ui.theme.KaMPKitTheme
import co.touchlab.kampkit.injectLogger
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kermit.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")
    private val viewModel: BreedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaMPKitTheme(isSystemInDarkTheme(), true) {
                MainScreen(viewModel, log)
            }
        }
    }
}
