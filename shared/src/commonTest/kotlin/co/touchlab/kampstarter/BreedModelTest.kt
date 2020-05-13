package co.touchlab.kampstarter

import co.touchlab.kampstarter.mock.KtorApiMock
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kampstarter.models.ItemDataSummary
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.CompletableDeferred
import kotlin.test.*

class BreedModelTest : BaseTest() {

    private lateinit var model: BreedModel
    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    private val itemDataSummary = CompletableDeferred<ItemDataSummary>()
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
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)
        model.getBreedsFromNetwork().join()
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)
    }

    @Test
    fun updateFavoriteTest() = runTest {
        ktorApi.mock.getJsonFromApi.returns(ktorApi.successResult())
        model.getBreedsFromNetwork().join()
        itemDataSummary.await(500)
        val breedOld = dbHelper.selectAllItems().executeAsList().first()
        assertFalse(breedOld.isFavorited())
        model.updateBreedFavorite(breedOld).join()
        val breedNew = dbHelper.selectById(breedOld.id).executeAsOne()
        assertTrue(breedNew.isFavorited())
    }

    @Test
    fun notifyErrorOnException() = runTest {
        ktorApi.mock.getJsonFromApi.throwOnCall(RuntimeException())

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

