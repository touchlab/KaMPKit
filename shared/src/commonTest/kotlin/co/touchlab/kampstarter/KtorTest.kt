package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.response.BreedResult
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue



abstract class KtorTest {

    private val ktorApiMock = KtorApiMock()

    @BeforeTest
    fun setup(){
    }

    @Test
    fun `Getting Json from api Success`() = runTest {
        val breedResult = ktorApiMock.getJsonFromApi()
        assertTrue(breedResult.status == "success",
            "Getting Json Failure")
    }
}

class KtorApiMock : KtorApi {

    var jsonRequested = false

    override suspend fun getJsonFromApi(): BreedResult {
        jsonRequested = true
        val map = mutableMapOf<String,List<String>>()
        map["appenzeller"] = listOf()
        map["australian"] = listOf("shepherd")
        return BreedResult(map as HashMap<String, List<String>>,"success")
    }

    override suspend fun setThingJson(value: String):Boolean {
        return true
    }



}