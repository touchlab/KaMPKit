package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kampstarter.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class NativeViewModel(
    private val viewUpdate: (ItemDataSummary) -> Unit,
    private val errorUpdate: (String) -> Unit
) : KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedModel") }
    private val scope = MainScope(Dispatchers.Main, log)
    private val breedModel: BreedModel

    init {
        ensureNeverFrozen()
        breedModel = BreedModel()
        observeBreeds()
    }

    private fun observeBreeds() {
        scope.launch {
            log.v { "Observe Breeds" }
            breedModel.selectAllBreeds()
                .collect { summary ->
                    log.v { "Collecting Things" }
                    viewUpdate(summary)
                }
        }
    }

    fun getBreedsFromNetwork() {
        scope.launch {
            breedModel.getBreedsFromNetwork()?.let { errorString ->
                errorUpdate(errorString)
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
