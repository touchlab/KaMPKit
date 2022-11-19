package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed

data class BreedRepoData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data: List<Breed> = emptyList()
)