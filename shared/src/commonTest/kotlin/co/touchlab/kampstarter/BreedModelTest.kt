package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kampstarter.models.ItemDataSummary
import co.touchlab.kampstarter.response.BreedResult
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlin.test.*

class BreedModelTest: BaseTest() {

    private lateinit var model: BreedModel
    private val kermit = Kermit()
    private var dbHelper = DatabaseHelper(testDbConnection(), kermit, Dispatchers.Default)
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    private val itemDataSummary = CompletableDeferred<ItemDataSummary>()
    private var errorString = CompletableDeferred<String>()

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, ktorApi, kermit)
        dbHelper.deleteAll()

        model = BreedModel()

        itemDataSummary.complete(model.selectAllBreeds().first())
    }

    @Test
    fun staleDataCheckTest() = runTest {
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY, currentTimeMillis())
        assertFalse(ktorApi.jsonRequested)
        model.getBreedsFromNetwork()?.let {
            errorString.complete(it)
        }
        assertFalse(ktorApi.jsonRequested)
    }

    @Test
    fun updateFavoriteTest() = runTest {

        model.getBreedsFromNetwork()?.let {
            errorString.complete(it)
        }
        itemDataSummary.await(500)
        val breedOld = dbHelper.selectAllItems().first().first()
        assertEquals("appenzeller", breedOld.name)
        assertFalse(breedOld.isFavorited())

        model.updateBreedFavorite(breedOld)

        val breedNew = dbHelper.selectById(breedOld.id).first().first()
        assertTrue(breedNew.isFavorited())
    }

    @Test
    fun notifyErrorOnException() = runTest {
        ktorApi.thowOnRequest = true

        model.getBreedsFromNetwork()?.let {
            errorString.complete(it)
        }

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
