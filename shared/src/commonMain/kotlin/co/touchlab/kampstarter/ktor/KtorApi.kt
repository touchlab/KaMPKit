package co.touchlab.kampstarter.ktor

import co.touchlab.kampstarter.response.BreedResult

interface KtorApi {
    suspend fun getJsonFromApi(): BreedResult
}