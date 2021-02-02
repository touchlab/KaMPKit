package co.touchlab.kampkit.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import kotlinx.coroutines.Job
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
    private var currentJob: Job = Job()

    companion object {
        private const val TAG = "BreedViewModel"
    }

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

    fun getBreeds(forced: Boolean = false) {
        currentJob.cancel()
        currentJob = viewModelScope.launch {
            Log.d(TAG, "getBreeds: Collecting Things")
            breedModel.getBreeds(forced).collect {
                _breedStateFlow.value = it
            }
        }
    }

    fun updateBreedFavorite(breed: Breed) {
        currentJob.cancel()
        currentJob = viewModelScope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }
}
