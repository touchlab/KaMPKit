package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.touchlab.kampkit.android.ui.theme.KaMPKitTheme
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaMPKitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel: BreedViewModel by viewModels()
                    val state by viewModel.breedStateFlow.collectAsState()
                    MainScreen(screenState = state, viewModel::updateBreedFavorite)
                }
            }
        }
    }
}

@Composable
fun MainScreen(screenState: DataState<ItemDataSummary>, favoriteBreed: (Breed) -> Unit) {
    // TODO Handle loading and pull down and error
    when (screenState) {
        is DataState.Success -> {DogList(breeds = screenState.data.allItems, favoriteBreed)}
        is DataState.Loading -> LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
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
            .padding(10.dp)
            .clickable { onClick(breed) }
    ) {
        Text(breed.name, Modifier.weight(1F))
        if (breed.favorite == 0L) {
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
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun BreedRowPreview() {
    KaMPKitTheme {
        DogRow(breed = Breed(0, "jack russel", 0)) {}
    }
}

@Preview(showBackground = true)
@Composable
fun DogListPreview() {
    KaMPKitTheme {
        DogList(breeds = sampleData) {}
    }
}

private val sampleData = listOf(
    Breed(1, "jack russel", 0),
    Breed(1, "mutt", 1),
    Breed(1, "chihuahua", 0),
    Breed(1, "doberman", 1),
    Breed(1, "pittbull", 0),
)