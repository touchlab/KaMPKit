package co.touchlab.kampkit.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BreedViewModel : ViewModel() {

    private var breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState.Loading
    )

    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow

    init {
        observeBreeds()
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            breedModel.getBreedsFromCache().collect {
                _breedStateFlow.value = it
            }
        }
    }

    fun getBreeds() {
        viewModelScope.launch {
            breedModel.getBreeds().collect {
                _breedStateFlow.value = it
            }
        }
    }
    fun updateBreedFavorite(breed: Breed) {
        viewModelScope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }
}
