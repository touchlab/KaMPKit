package co.touchlab.kampstarter

import co.touchlab.kampstarter.models.BreedModel
import com.russhwolf.settings.MockSettings
import kotlin.test.*

abstract class BreedModelTest {

    private lateinit var model:BreedModel
    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    @BeforeTest
    fun setup(){
        TestingServiceRegistry.appStart(dbHelper,settings,ktorApi)

        model = BreedModel{

        }
    }

    @Test
    fun `Breed List is Stale`() = runTest {
        assertTrue(model.isBreedListStale(currentTimeMillis()))
    }

    @Test
    fun `Breed List is not stale`() = runTest {
        model.getBreedsFromNetwork(currentTimeMillis())
        assertFalse(model.isBreedListStale(currentTimeMillis()))
    }

    @Test
    fun `Get Breed List Success`() = runTest {
        assertFalse(ktorApi.getJsonCalled)
        model.getBreedsFromNetwork(currentTimeMillis())
        assertTrue(ktorApi.getJsonCalled)
    }

    @Test
    fun `Get Breed List Failure`() = runTest {
        val currentTime = currentTimeMillis()
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY,currentTime)
        assertFalse(ktorApi.getJsonCalled)
        model.getBreedsFromNetwork(currentTime)
        assertFalse(ktorApi.getJsonCalled)
    }

    @Test
    fun `Update Breed Favorite`() = runTest {
        model.getBreedsFromNetwork(currentTimeMillis())
        val breedOld = dbHelper.selectAllItems().executeAsList().first()
        model.updateBreedFavorite(breedOld.id,true)
        val breedNew = dbHelper.selectItemById(breedOld.id).executeAsOne()
        assertTrue(breedNew.isFavorited())
    }

    @AfterTest
    fun breakdown(){
        TestingServiceRegistry.appEnd()
    }
}