package co.touchlab.kampkit.mock

import co.touchlab.kampkit.data.dog.DogApi
import co.touchlab.kampkit.data.dog.DogResult

// TODO convert this to use Ktor's MockEngine
class DogApiMock : DogApi {
    private var nextResult: () -> DogResult = { error("Uninitialized!") }
    var calledCount = 0
        private set

    override suspend fun getJsonFromApi(): DogResult {
        val result = nextResult()
        calledCount++
        return result
    }

    fun successResult(): DogResult {
        val map = HashMap<String, List<String>>().apply {
            put("appenzeller", emptyList())
            put("australian", listOf("shepherd"))
        }
        return DogResult(map, "success")
    }

    fun prepareResult(dogResult: DogResult) {
        nextResult = { dogResult }
    }

    fun throwOnCall(throwable: Throwable) {
        nextResult = { throw throwable }
    }
}
