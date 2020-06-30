package co.touchlab.kampkit

import co.touchlab.kampkit.mock.KtorApiMock
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BreedModelTest : BaseTest() {

    private lateinit var model: BreedModel
    private val kermit = Kermit()
    private var dbHelper = DatabaseHelper(
        testDbConnection(),
        kermit,
        Dispatchers.Default
    )
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, ktorApi, kermit)
        dbHelper.deleteAll()
        model = BreedModel()
        model.selectAllBreeds().first()
    }

    @Test
    fun staleDataCheckTest() = runTest {
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY, currentTimeMillis())
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)

        assertNull(model.getBreedsFromNetwork())
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)
    }

    @Test
    fun updateFavoriteTest() = runTest {
        ktorApi.mock.getJsonFromApi.returns(ktorApi.successResult())
        assertNull(model.getBreedsFromNetwork())
        val breedOld = dbHelper.selectAllItems().first().first()
        assertFalse(breedOld.isFavorited())

        model.updateBreedFavorite(breedOld)

        val breedNew = dbHelper.selectById(breedOld.id).first().first()
        assertEquals(breedNew.id, breedOld.id)
        assertTrue(breedNew.isFavorited())
    }

    @Test
    fun notifyErrorOnException() = runTest {
        ktorApi.mock.getJsonFromApi.throwOnCall(RuntimeException())

        assertNotNull(model.getBreedsFromNetwork())
    }

    @AfterTest
    fun breakdown() = runTest {
        dbHelper.deleteAll()
        appEnd()
    }
}
