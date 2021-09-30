package co.touchlab.kampkit.viewmodel

import co.touchlab.kampkit.db.Breed

class SharedLongestBreedViewModel() : SharedBreedViewModel(), LongestBreedViewModelInterface {

    override fun getLongestBreed(): Breed? {
        return getFlowValue().data?.longestItem
    }
}
