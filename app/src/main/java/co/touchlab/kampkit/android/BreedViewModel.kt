package co.touchlab.kampkit.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.ItemDataSummary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BreedViewModel : ViewModel() {

    private var breedModel: BreedModel = BreedModel()
    val breedLiveData = MutableLiveData<ItemDataSummary>()
    val errorLiveData = MutableLiveData<String>()

    init {
        observeBreeds()
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            breedModel.selectAllBreeds().collect {
                breedLiveData.postValue(it)
            }
        }
    }

    fun getBreedsFromNetwork() {
        viewModelScope.launch {
            breedModel.getBreedsFromNetwork()?.let { errorString ->
                errorLiveData.postValue(errorString)
            }
        }
    }
    fun updateBreedFavorite(breed: Breed) {
        viewModelScope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }
}
