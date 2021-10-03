package co.touchlab.kampkit.vm

import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import kotlinx.coroutines.flow.StateFlow

class BreedViewModel : CommonBreedViewModel() {
    // On android we expose this state directly
    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow
}
