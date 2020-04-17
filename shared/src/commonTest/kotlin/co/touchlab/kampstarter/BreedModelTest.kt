package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kampstarter.models.ItemDataSummary
import co.touchlab.kampstarter.response.BreedResult
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.CompletableDeferred
import kotlin.test.*

class BreedModelTest: BaseTest() {

    private lateinit var model: BreedModel
    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    private var itemDataSummary = CompletableDeferred<ItemDataSummary>()
    private var errorString = CompletableDeferred<String>()

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, ktorApi)
        dbHelper.deleteAll()

        model = BreedModel(viewUpdate = { summary ->
            if (summary.allItems.isNotEmpty())
                itemDataSummary.complete(summary)
        }, errorUpdate = { s ->
            errorString.complete(s)
        })
    }

    @Test
    fun staleDataCheckTest() = runTest {
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY, currentTimeMillis())
        assertFalse(ktorApi.jsonRequested)
        model.getBreedsFromNetwork().join()
        assertFalse(ktorApi.jsonRequested)
    }

    @Test
    fun updateFavoriteTest() = runTest {
        model.getBreedsFromNetwork().join()
        itemDataSummary.await(500)
        val breedOld = dbHelper.selectAllItems().executeAsList().first()
        assertEquals("appenzeller", breedOld.name)
        assertFalse(breedOld.isFavorited())
        model.updateBreedFavorite(breedOld).join()
        val breedNew = dbHelper.selectById(breedOld.id).executeAsOne()
        assertTrue(breedNew.isFavorited())
    }

    @Test
    fun notifyErrorOnException() = runTest {
        ktorApi.thowOnRequest = true

        model.getBreedsFromNetwork().join()
        val error = errorString.await(500)
        assertNotNull(error)
    }

    @AfterTest
    fun breakdown() = runTest {
        dbHelper.deleteAll()
        appEnd()
    }
}

class KtorApiMock : KtorApi {
    var jsonRequested = false
    var thowOnRequest = false

    override suspend fun getJsonFromApi(): BreedResult {
        if (thowOnRequest) {
            throw Exception()
        }

        jsonRequested = true
        val map = mutableMapOf<String, List<String>>()
        map["appenzeller"] = listOf()
        map["australian"] = listOf("shepherd")
        return BreedResult(map as HashMap<String, List<String>>, "success")
    }
}
