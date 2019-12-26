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
    fun `Get Breed List Success`() = runTest {
        assertFalse(ktorApi.getJsonCalled)
        model.getBreedsFromNetwork()
        assertTrue(ktorApi.getJsonCalled)
    }

    @Test
    fun `Get Breed List Failure`() = runTest {
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY,currentTimeMillis())
        assertFalse(ktorApi.getJsonCalled)
        model.getBreedsFromNetwork()
        assertFalse(ktorApi.getJsonCalled)
    }

    @Test
    fun `Update Breed Favorite`() = runTest {
        model.getBreedsFromNetwork()
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