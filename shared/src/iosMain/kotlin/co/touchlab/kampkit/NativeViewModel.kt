package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class NativeViewModel(
    private val onLoading: () -> Unit,
    private val onSuccess: (ItemDataSummary) -> Unit,
    private val onError: (String) -> Unit,
    private val onEmpty: () -> Unit
) : KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedModel") }
    private val scope = MainScope(Dispatchers.Main, log)
    private val breedModel: BreedModel

    init {
        ensureNeverFrozen()
        breedModel = BreedModel()
        getBreeds()
    }

    fun getBreeds() {
        scope.launch {
            log.v { "Observe Breeds" }
            breedModel.getBreeds()
                .collect { dataState ->
                    log.v { "Collecting Things" }
                    when (dataState) {
                        is DataState.Success -> {
                            onSuccess(dataState.data)
                        }
                        is DataState.Error -> {
                            onError(dataState.exception)
                        }
                        DataState.Empty -> {
                            onEmpty()
                        }
                        DataState.Loading -> {
                            onLoading()
                        }
                    }
                }
        }
    }

    fun updateBreedFavorite(breed: Breed) {
        scope.launch {
            breedModel.updateBreedFavorite(breed)
        }
    }

    fun onDestroy() {
        scope.onDestroy()
    }
}
