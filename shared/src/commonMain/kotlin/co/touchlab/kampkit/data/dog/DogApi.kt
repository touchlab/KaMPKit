package co.touchlab.kampkit.data.dog

interface DogApi {
    suspend fun getJsonFromApi(): DogResult
}
