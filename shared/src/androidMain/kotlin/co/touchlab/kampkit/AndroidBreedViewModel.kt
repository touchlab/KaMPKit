package co.touchlab.kampkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class AndroidBreedViewModel : ViewModel(),
    BreedViewModel by BreedViewModelImpl() {

    init {
        initWithScope(viewModelScope)
    }
}
