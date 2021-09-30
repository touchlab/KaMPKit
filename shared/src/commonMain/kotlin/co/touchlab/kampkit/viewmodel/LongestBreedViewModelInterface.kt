package co.touchlab.kampkit.viewmodel

import co.touchlab.kampkit.db.Breed

interface LongestBreedViewModelInterface : BreedViewModelInterface {
    fun getLongestBreed(): Breed?
}
