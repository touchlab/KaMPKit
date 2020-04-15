package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kampstarter.models.ItemDataSummary
import co.touchlab.kampstarter.response.BreedResult
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.*
import kotlin.test.*

abstract class BreedModelTest {

    private lateinit var model: BreedModel
    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    private var itemDataSummary: ItemDataSummary? = null
    private var errorString: String? = null

    @BeforeTest
    fun setup() {
        TestingServiceRegistry.appStart(dbHelper, settings, ktorApi)

        model = BreedModel(viewUpdate = { summary ->
            itemDataSummary = summary;
        }, errorUpdate = { s ->
            errorString = s
        })
    }

    @Test
    fun `Get Breed List Failure`() = runTest {
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY,currentTimeMillis())
        assertFalse(ktorApi.jsonRequested)
        model.getBreedsFromNetwork()
        assertFalse(ktorApi.jsonRequested)
    }

    @Test
    fun `Update Breed Favorite`() = runTest {
        model.getBreedsFromNetwork()
        val breedOld = dbHelper.selectAllItems().executeAsList().first()
        model.updateBreedFavorite(breedOld)
        val breedNew = dbHelper.selectById(breedOld.id).executeAsOne()
        assertTrue(breedNew.isFavorited())
    }

    @Test
    fun `Should notify error update callback on exception`() = runTest {
        ktorApi.thowOnRequest = true
        model.getBreedsFromNetwork()
        assertNotNull(errorString)
    }

    @Test
    fun `Should notify view update callback on summary available`() = runTestWithFlow {
        ktorApi.thowOnRequest = false
        model.getBreedsFromNetwork()
        assertNotNull(itemDataSummary)
    }

    @AfterTest
    fun breakdown(){
        TestingServiceRegistry.appEnd()
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
        val map = mutableMapOf<String,List<String>>()
        map["appenzeller"] = listOf()
        map["australian"] = listOf("shepherd")
        return BreedResult(map as HashMap<String, List<String>>,"success")
    }
}
