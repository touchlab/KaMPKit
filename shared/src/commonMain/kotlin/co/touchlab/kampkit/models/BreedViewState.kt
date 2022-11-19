package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed

data class BreedViewState(
    val breeds: List<Breed>? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)
