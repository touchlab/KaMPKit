package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.response.BreedResult

interface KtorApi {
    suspend fun getJsonFromApi(): BreedResult
}
