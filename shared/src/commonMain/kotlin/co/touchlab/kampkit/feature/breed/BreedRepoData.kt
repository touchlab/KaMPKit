package co.touchlab.kampkit.feature.breed

import co.touchlab.kampkit.db.Breed

data class BreedRepoData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data: List<Breed> = emptyList()
)