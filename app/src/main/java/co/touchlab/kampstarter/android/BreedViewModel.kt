package co.touchlab.kampstarter.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.models.BreedModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampstarter.models.ItemDataSummary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BreedViewModel : ViewModel() {

    private var breedModel: BreedModel
    val breedLiveData = MutableLiveData<ItemDataSummary>()
    val errorLiveData = MutableLiveData<String>()

    init {
        breedModel = BreedModel(errorUpdate = { errorMessage ->
            errorLiveData.postValue(errorMessage)
        })
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
            breedModel.getBreedsFromNetwork()
        }
    }
    fun updateBreedFavorite(breed: Breed){
        viewModelScope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }

    fun onDestroy() {
        breedModel.onDestroy()
    }
}