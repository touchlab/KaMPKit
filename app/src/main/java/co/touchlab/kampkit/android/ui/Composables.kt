package co.touchlab.kampkit.android.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.touchlab.kampkit.android.R
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary

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
        targetState = breed.favorite == 0L,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    ) { fav ->
        if (fav) {
            Image(
                painter = painterResource(id = R.drawable.ic_favorite_border_24px),
                contentDescription = "Favorite $breed"
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_favorite_24px),
                contentDescription = "Unfavorite $breed"
            )
        }
    }
}

@Composable
fun Loading(
    lastState: DataState<ItemDataSummary>,
    favoriteBreed: (Breed) -> Unit
) {
    when (lastState) {
        DataState.Loading -> {
            // Nothing here, because it's taken care of by SwipeRefresh
        }
        DataState.Empty -> {
            Empty()
        }
        is DataState.Error -> {
            Error(errorState = lastState)
        }
        is DataState.Success -> {
            Success(lastState, favoriteBreed)
        }
    }
}

@Composable
fun Empty() {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sorry, no doggos found")
    }
}

@Composable
fun Error(errorState: DataState.Error) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = errorState.exception)
    }
}

@Composable
fun Success(
    successState: DataState.Success<ItemDataSummary>,
    favoriteBreed: (Breed) -> Unit
) {
    DogList(breeds = successState.data.allItems, favoriteBreed)
}

@Composable
fun MainScreen(
    dataState: DataState<ItemDataSummary>,
    favoriteBreed: (Breed) -> Unit
) {
    val lastState: MutableState<DataState<ItemDataSummary>> = remember { mutableStateOf(dataState) }
    when (dataState) {
        DataState.Loading -> {
            Loading(lastState = lastState.value, favoriteBreed = favoriteBreed)
        }
        DataState.Empty -> {
            lastState.value = dataState
            Empty()
        }
        is DataState.Error -> {
            lastState.value = dataState
            Error(errorState = dataState)
        }
        is DataState.Success<ItemDataSummary> -> {
            lastState.value = dataState
            Success(successState = dataState, favoriteBreed)
        }
    }
}
