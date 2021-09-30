package co.touchlab.kampkit.viewmodel

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.DataState

class SharedLongestBreedViewModel() : SharedBreedViewModel(), LongestBreedViewModelInterface {

    override fun getLongestBreed(): Breed? {
        return getFlowValue().run {
            if(this is DataState.Success) {
                data.longestItem
            }
            null
        }
    }
}