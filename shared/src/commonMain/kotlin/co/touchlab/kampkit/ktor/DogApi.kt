package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.response.BreedResult

internal interface DogApi {
    suspend fun getJsonFromApi(): BreedResult
}
