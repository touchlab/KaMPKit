package co.touchlab.kampkit.mock

import co.touchlab.kampkit.ktor.DogApi
import co.touchlab.kampkit.response.BreedResult

// TODO convert this to use Ktor's MockEngine
class DogApiMock : DogApi {
    private var nextResult: () -> BreedResult = { error("Uninitialized!") }
    var calledCount = 0
        private set

    override suspend fun getJsonFromApi(): BreedResult {
        val result = nextResult()
        calledCount++
        return result
    }

    fun successResult(): BreedResult {
        val map = HashMap<String, List<String>>().apply {
            put("appenzeller", emptyList())
            put("australian", listOf("shepherd"))
        }
        return BreedResult(map, "success")
    }

    fun prepareResult(breedResult: BreedResult) {
        nextResult = { breedResult }
    }

    fun throwOnCall(throwable: Throwable) {
        nextResult = { throw throwable }
    }
}
