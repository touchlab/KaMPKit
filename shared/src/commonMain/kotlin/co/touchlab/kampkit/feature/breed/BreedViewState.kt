package co.touchlab.kampkit.feature.breed

import co.touchlab.kampkit.db.Breed

data class BreedViewState(
    val breeds: List<Breed>? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)
