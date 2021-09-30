package co.touchlab.kampkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.viewmodel.BreedViewModelInterface
import co.touchlab.kampkit.viewmodel.SharedBreedViewModel

class BreedViewModel :
    ViewModel(),
    BreedViewModelInterface by SharedBreedViewModel() {

    init {
        initWithScope(viewModelScope)
    }
}
