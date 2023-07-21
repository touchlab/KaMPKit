package co.touchlab.kampkit.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kampkit.MR
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kampkit.models.BreedViewState
import co.touchlab.kermit.Logger
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: BreedViewModel,
    log: Logger
) {
    val dogsState by viewModel.breedState.collectAsState()
    val scope = rememberCoroutineScope()

    MainScreenContent(
        dogsState = dogsState,
        onRefresh = { scope.launch { viewModel.refreshBreeds() } },
        onSuccess = { data -> log.v { "View updating with ${data.size} breeds" } },
        onError = { exception -> log.e { "Displaying error: $exception" } },
        onFavorite = { scope.launch { viewModel.updateBreedFavorite(it) } }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    dogsState: BreedViewState,
    onRefresh: () -> Unit = {},
    onSuccess: (List<Breed>) -> Unit = {},
    onError: (String) -> Unit = {},
    onFavorite: (Breed) -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val refreshState = rememberPullRefreshState(dogsState.isLoading, onRefresh)

        Box(Modifier.pullRefresh(refreshState)) {
            if (dogsState.isEmpty) {
                Empty()
            }
            val breeds = dogsState.breeds
            if (breeds != null) {
                LaunchedEffect(breeds) {
                    onSuccess(breeds)
                }
                Success(successData = breeds, favoriteBreed = onFavorite)
            }
            val error = dogsState.error
            if (error != null) {
                LaunchedEffect(error) {
                    onError(error)
                }
                Error(error)
            }

            PullRefreshIndicator(dogsState.isLoading, refreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun Empty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(MR.strings.empty_breeds))
    }
}

@Composable
fun Error(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
    }
}

@Composable
fun Success(
    successData: List<Breed>,
    favoriteBreed: (Breed) -> Unit
) {
    DogList(breeds = successData, favoriteBreed)
}

@Composable
fun DogList(breeds: List<Breed>, onItemClick: (Breed) -> Unit) {
    LazyColumn {
        items(breeds) { breed ->
            DogRow(breed) {
                onItemClick(it)
            }
            Divider()
        }
    }
}

@Composable
fun DogRow(breed: Breed, onClick: (Breed) -> Unit) {
    Row(
        Modifier
            .clickable { onClick(breed) }
            .padding(10.dp)
    ) {
        Text(breed.name, Modifier.weight(1F))
        FavoriteIcon(breed)
    }
}

@Composable
fun FavoriteIcon(breed: Breed) {
    Crossfade(
        targetState = !breed.favorite,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    ) { fav ->
        if (fav) {
            Image(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(MR.strings.favorite_breed, breed.name)
            )
        } else {
            Image(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(MR.strings.unfavorite_breed, breed.name)
            )
        }
    }
}
