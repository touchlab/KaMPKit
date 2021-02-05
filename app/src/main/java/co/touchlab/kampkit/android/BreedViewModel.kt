package co.touchlab.kampkit.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class BreedViewModel : ViewModel(), KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedViewModel") }
    private val breedModel: BreedModel = BreedModel()
    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState.Loading
    )

    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow
    private var currentJob: Job = Job()

    init {
        getBreeds()
    }

    fun getBreeds(forced: Boolean = false) {
        if (currentJob.isActive) {
            currentJob.cancel()
        }
        currentJob = viewModelScope.launch {
            log.v { "getBreeds: Collecting Things" }
            breedModel.getBreeds(forced).collect {
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
