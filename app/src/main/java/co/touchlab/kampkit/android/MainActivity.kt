package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import co.touchlab.kampkit.android.ui.MainScreen
import co.touchlab.kampkit.android.ui.theme.KaMPKitTheme
import co.touchlab.kampkit.vm.BreedViewModel
import co.touchlab.kermit.Kermit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Kermit by inject { parametersOf("MainActivity") }

    // As this ViewModel is a KoinComponent, we can use the default lifecycle method/delegate
    private val viewModel: BreedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaMPKitTheme {
                MainScreen(viewModel, log)
            }
        }
        if (viewModel.breedStateFlow.value.data == null) {
            viewModel.refreshBreeds()
        }
    }
}
