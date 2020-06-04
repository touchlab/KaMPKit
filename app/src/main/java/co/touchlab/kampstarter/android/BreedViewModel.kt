package co.touchlab.kampstarter.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.models.BreedModel
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.lifecycle.viewModelScope
import co.touchlab.kampstarter.models.ItemDataSummary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class BreedViewModel(app: Application) : AndroidViewModel(app) {

    private var breedModel: BreedModel
    val breedLiveData = MutableLiveData<ItemDataSummary>()
    val errorLiveData = MutableLiveData<String>()

    init {
        breedModel = BreedModel(errorUpdate = { errorMessage ->
            errorLiveData.postValue(errorMessage)
        })
        observeBreeds()
    }


    @InternalCoroutinesApi
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