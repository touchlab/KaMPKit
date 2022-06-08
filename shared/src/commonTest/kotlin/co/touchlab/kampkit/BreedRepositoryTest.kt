package co.touchlab.kampkit

import app.cash.turbine.test
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.mock.ClockMock
import co.touchlab.kampkit.mock.DogApiMock
import co.touchlab.kampkit.models.BreedRepository
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.Duration.Companion.hours

@RunWith(AndroidJUnit4::class)
class BreedRepositoryTest {

    private var kermit = Logger(StaticConfig())
    private var testDbConnection = testDbConnection()
    private val settings = MockSettings()
    private val ktorApi = DogApiMock()

    // Need to start at non-zero time because the default value for db timestamp is 0
    private val clock = ClockMock(Clock.System.now())


    companion object {
        private val appenzeller = Breed(1, "appenzeller", false)
        private val australianNoLike = Breed(2, "australian", false)
        private val australianLike = Breed(2, "australian", true)
        private val breedsNoFavorite = listOf(appenzeller, australianNoLike)
        private val breedsFavorite = listOf(appenzeller, australianLike)
        private val breedNames = breedsFavorite.map { it.name }
    }

    @AfterTest
    fun tearDown() = runTest {
        testDbConnection.close()
    }

    @Test
    fun `Get breeds without cache`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            testDispatcher
        )

        ktorApi.prepareResult(ktorApi.successResult())
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        repository.refreshBreeds()
        repository.getBreeds().test {
            assertEquals(breedsNoFavorite, awaitItem())
        }
    }

    @Test
    fun `Get updated breeds with cache and preserve favorites`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)

        val successResult = ktorApi.successResult()
        val resultWithExtraBreed = successResult.copy(message = successResult.message + ("extra" to emptyList()))
        ktorApi.prepareResult(resultWithExtraBreed)

        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        repository.getBreeds().test {
            assertEquals(breedsFavorite, awaitItem())
            expectNoEvents()

            repository.refreshBreeds()
            // id is 5 here because it incremented twice when trying to insert duplicate breeds
            assertEquals(breedsFavorite + Breed(5, "extra", false), awaitItem())
        }
    }

    @Test
    fun `Get updated breeds when stale and preserve favorites`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        settings.putLong(BreedRepository.DB_TIMESTAMP_KEY, (clock.currentInstant - 2.hours).toEpochMilliseconds())

        val successResult = ktorApi.successResult()
        val resultWithExtraBreed = successResult.copy(message = successResult.message + ("extra" to emptyList()))
        ktorApi.prepareResult(resultWithExtraBreed)

        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        launch {
            repository.refreshBreedsIfStale()
            repository.getBreeds().test {
                // id is 5 here because it incremented twice when trying to insert duplicate breeds
                assertEquals(breedsFavorite + Breed(5, "extra", false), awaitItem())
            }
        }
    }

    @Test
    fun `Toggle favorite cached breed`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        launch {
            repository.getBreeds().test {
                assertEquals(breedsFavorite, awaitItem())
                expectNoEvents()

                repository.updateBreedFavorite(australianLike)
                assertEquals(breedsNoFavorite, awaitItem())
            }
        }
    }

    @Test
    fun `No web call if data is not stale`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        settings.putLong(BreedRepository.DB_TIMESTAMP_KEY, clock.currentInstant.toEpochMilliseconds())
        ktorApi.prepareResult(ktorApi.successResult())

        repository.refreshBreedsIfStale()
        assertEquals(0, ktorApi.calledCount)

        repository.refreshBreeds()
        assertEquals(1, ktorApi.calledCount)
    }

    @Test
    fun `Rethrow on API error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        ktorApi.throwOnCall(RuntimeException("Test error"))

        val throwable = assertFails {
            repository.refreshBreeds()
        }
        assertEquals("Test error", throwable.message)
    }

    @Test
    fun `Rethrow on API error when stale`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val dbHelper = DatabaseHelper(
            testDbConnection,
            kermit,
            dispatcher
        )
        val repository: BreedRepository = BreedRepository(dbHelper, settings, ktorApi, kermit, clock)
        settings.putLong(BreedRepository.DB_TIMESTAMP_KEY, (clock.currentInstant - 2.hours).toEpochMilliseconds())
        ktorApi.throwOnCall(RuntimeException("Test error"))

        val throwable = assertFails {
            repository.refreshBreedsIfStale()
        }
        assertEquals("Test error", throwable.message)
    }
}
