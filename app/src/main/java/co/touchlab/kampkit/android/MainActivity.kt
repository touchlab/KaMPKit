package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import co.touchlab.kampkit.android.ui.MainScreen
import co.touchlab.kampkit.android.ui.theme.KaMPKitTheme
import co.touchlab.kampkit.models.DataState
import co.touchlab.kermit.Kermit
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), KoinComponent {

    private val log: Kermit by inject { parametersOf("MainActivity") }
    private val viewModel: BreedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaMPKitTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val dogsState by viewModel.breedStateFlow.collectAsState()
                    val isRefreshingState by remember(dogsState) {
                        derivedStateOf {
                            dogsState is DataState.Loading
                        }
                    }

                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshingState),
                        onRefresh = {
                            viewModel.refreshBreeds(true)
                        }
                    ) {
                        MainScreen(dataState = dogsState, viewModel::updateBreedFavorite)
                    }
                }
            }
        }

        viewModel.refreshBreeds()
    }
}
