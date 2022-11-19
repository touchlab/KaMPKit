package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.base.ApiStatus
import co.touchlab.kampkit.ktor.dto.BreedDto

interface Api {

    suspend fun getBreeds(): ApiStatus<BreedDto>

}