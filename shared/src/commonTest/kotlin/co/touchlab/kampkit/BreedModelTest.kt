package co.touchlab.kampkit

import app.cash.turbine.test
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.mock.ClockMock
import co.touchlab.kampkit.mock.KtorApiMock
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.hours

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

    // Need to start at non-zero time because the default value for db timestamp is 0
    private val clock = ClockMock(Clock.System.now())

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, ktorApi, kermit, clock)
        dbHelper.deleteAll()
        model = BreedModel()
        model.getBreedsFromCache().first()
    }

    @Test
    fun staleDataCheckTest() = runTest {
        val currentTimeMS = Clock.System.now().toEpochMilliseconds()
        settings.putLong(BreedModel.DB_TIMESTAMP_KEY, currentTimeMS)
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)

        val expectedError = DataState.Error("Unable to download breed list")
        val actualError = model.getBreedsFromNetwork(0L)

        assertEquals(
            expectedError,
            actualError
        )
        assertTrue(ktorApi.mock.getJsonFromApi.calledCount == 0)
    }

    @ExperimentalTime
    @Test
    fun updateFavoriteTest() = runTest {
        ktorApi.mock.getJsonFromApi.returns(ktorApi.successResult())
        dbHelper.deleteAll()
        val appenzeller = Breed(1, "appenzeller", 0L)
        val australianNoLike = Breed(2, "australian", 0L)
        val australianLike = Breed(2, "australian", 1L)

        val dataStateSuccessNoFavorite = DataState.Success(
            ItemDataSummary(appenzeller, listOf(appenzeller, australianNoLike))
        )

        val dataStateSuccessFavorite = DataState.Success(
            ItemDataSummary(appenzeller, listOf(appenzeller, australianLike))
        )

        model.getBreeds().test {
            assertEquals(DataState.Loading, expectItem())
            assertEquals(dataStateSuccessNoFavorite, expectItem())
            model.updateBreedFavorite(australianNoLike)
            assertEquals(dataStateSuccessFavorite, expectItem())
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun updateDatabaseTest() = runTest {
        val successResult = ktorApi.successResult()
        ktorApi.mock.getJsonFromApi.returns(successResult)
        model.getBreeds().test {
            assertEquals(DataState.Loading, expectItem())
            val oldBreeds = expectItem()
            assertTrue(oldBreeds is DataState.Success)
            assertEquals(ktorApi.successResult().message.keys.size, oldBreeds.data.allItems.size)
        }

        // Advance time by more than an hour to make cached data stale
        clock.currentInstant += 2.hours
        val resultWithExtraBreed = successResult.copy().apply { message["extra"] = emptyList() }

        ktorApi.mock.getJsonFromApi.returns(resultWithExtraBreed)
        model.getBreeds().test {
            assertEquals(DataState.Loading, expectItem())
            val updated = expectItem()
            assertTrue(updated is DataState.Success)
            assertEquals(resultWithExtraBreed.message.keys.size, updated.data.allItems.size)
        }
    }

    @Test
    fun notifyErrorOnException() = runTest {
        ktorApi.mock.getJsonFromApi.throwOnCall(RuntimeException())
        assertNotNull(model.getBreedsFromNetwork(0L))
    }

    @AfterTest
    fun breakdown() = runTest {
        dbHelper.deleteAll()
        appEnd()
    }
}
