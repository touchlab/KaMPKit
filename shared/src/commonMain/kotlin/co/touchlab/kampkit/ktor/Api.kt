package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.base.ApiStatus
import co.touchlab.kampkit.response.BreedResult

interface Api {

    suspend fun getBreeds(): ApiStatus<BreedResult>

}