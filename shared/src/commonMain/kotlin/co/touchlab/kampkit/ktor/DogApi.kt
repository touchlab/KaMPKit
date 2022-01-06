package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.response.BreedResult

interface DogApi {
    suspend fun getJsonFromApi(): BreedResult
}
