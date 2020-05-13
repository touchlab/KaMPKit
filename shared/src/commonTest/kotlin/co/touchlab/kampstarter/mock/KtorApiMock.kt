package co.touchlab.kampstarter.mock

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.response.BreedResult
import co.touchlab.karmok.MockManager

class KtorApiMock:KtorApi{
    internal val mock = InnerMock()
    override suspend fun getJsonFromApi(): BreedResult {
        return mock.getJsonFromApi.invokeSuspend({ getJsonFromApi() }, listOf())
    }

    class InnerMock(delegate: Any? = null) : MockManager(delegate) {
        internal val getJsonFromApi = MockFunctionRecorder<KtorApiMock, BreedResult>()
    }

    fun successResult(): BreedResult {
        val map = HashMap<String, List<String>>().apply {
            put("appenzeller", emptyList())
            put("australian", listOf("shepherd"))
        }
        return BreedResult(map, "success")
    }
}