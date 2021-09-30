package co.touchlab.kampkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.viewmodel.LongestBreedViewModelInterface
import co.touchlab.kampkit.viewmodel.SharedLongestBreedViewModel

class LongestBreedViewModel :
    ViewModel(),
    LongestBreedViewModelInterface by SharedLongestBreedViewModel() {

    init {
        initWithScope(viewModelScope)
        getLongestBreed()
    }
}
