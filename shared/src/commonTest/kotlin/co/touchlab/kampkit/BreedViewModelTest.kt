package co.touchlab.kampkit

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.mock.ClockMock
import co.touchlab.kampkit.mock.DogApiMock
import co.touchlab.kampkit.models.BreedRepository
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kampkit.models.BreedViewState
import co.touchlab.kampkit.response.BreedResult
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.russhwolf.settings.MapSettings
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock

class BreedViewModelTest {
    private var kermit = Logger(StaticConfig())
    private var testDbConnection = testDbConnection()
    private var dbHelper = DatabaseHelper(
        testDbConnection,
        kermit,
        Dispatchers.Default
    )
    private val settings = MapSettings()
    private val ktorApi = DogApiMock()

    // Need to start at non-zero time because the default value for db timestamp is 0
    private val clock = ClockMock(Clock.System.now())

    private val repository: BreedRepository =
        BreedRepository(dbHelper, settings, ktorApi, kermit, clock)

    @OptIn(DelicateCoroutinesApi::class)
    private val viewModel by lazy {
        BreedViewModel(repository, kermit)
            .also { GlobalScope.launch { it.activate() } }
    }

    companion object {
        private val appenzeller = Breed(1, "appenzeller", false)
        private val australianNoLike = Breed(2, "australian", false)
        private val australianLike = Breed(2, "australian", true)
        private val breedViewStateSuccessNoFavorite = BreedViewState.Content(
            breeds = listOf(appenzeller, australianNoLike)
        )
        private val breedViewStateSuccessFavorite = BreedViewState.Content(
            breeds = listOf(appenzeller, australianLike)
        )
        private val breedNames = breedViewStateSuccessNoFavorite.breeds.map { it.name }
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDbConnection.close()
    }

    @Test
    fun `Get breeds without cache`() = runTest {
        ktorApi.prepareResult(ktorApi.successResult())

        viewModel.breedState.test {
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(BreedViewState.Initial, BreedViewState.Empty())
            )
        }
    }

    @Test
    fun `Get breeds empty`() = runTest {
        ktorApi.prepareResult(BreedResult(emptyMap(), "success"))

        viewModel.breedState.test {
            assertEquals(
                BreedViewState.Empty(),
                awaitItemPrecededBy(BreedViewState.Initial)
            )
        }
    }

    @Test
    fun `Get updated breeds with cache and preserve favorites`() = runTest {
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            clock.currentInstant.toEpochMilliseconds()
        )

        val successResult = ktorApi.successResult()
        val resultWithExtraBreed = successResult.copy(
            message = successResult.message + ("extra" to emptyList())
        )
        ktorApi.prepareResult(resultWithExtraBreed)

        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        viewModel.breedState.test {
            assertEquals(breedViewStateSuccessFavorite, awaitItemPrecededBy(BreedViewState.Initial))
            expectNoEvents()

            viewModel.refreshBreeds()
            // id is 5 here because it incremented twice when trying to insert duplicate breeds
            assertEquals(
                BreedViewState.Content(
                    breedViewStateSuccessFavorite.breeds + Breed(5, "extra", false)
                ),
                awaitItemPrecededBy(breedViewStateSuccessFavorite.copy(isLoading = true))
            )
        }
    }

    @Test
    fun `Get updated breeds when stale and preserve favorites`() = runTest {
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            (clock.currentInstant - 2.hours).toEpochMilliseconds()
        )

        val successResult = ktorApi.successResult()
        val resultWithExtraBreed = successResult.copy(
            message = successResult.message + ("extra" to emptyList())
        )
        ktorApi.prepareResult(resultWithExtraBreed)

        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        viewModel.breedState.test {
            // id is 5 here because it incremented twice when trying to insert duplicate breeds
            assertEquals(
                BreedViewState.Content(
                    breedViewStateSuccessFavorite.breeds + Breed(5, "extra", false)
                ),
                awaitItemPrecededBy(BreedViewState.Initial, breedViewStateSuccessFavorite)
            )
        }
    }

    @Test
    fun `Toggle favorite cached breed`() = runTest {
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            clock.currentInstant.toEpochMilliseconds()
        )

        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)

        viewModel.breedState.test {
            assertEquals(breedViewStateSuccessFavorite, awaitItemPrecededBy(BreedViewState.Initial))
            expectNoEvents()

            viewModel.updateBreedFavorite(australianLike)
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(breedViewStateSuccessFavorite.copy(isLoading = true))
            )
        }
    }

    @Test
    fun `No web call if data is not stale`() = runTest {
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            clock.currentInstant.toEpochMilliseconds()
        )
        ktorApi.prepareResult(ktorApi.successResult())
        dbHelper.insertBreeds(breedNames)

        viewModel.breedState.test {
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(BreedViewState.Initial)
            )
            assertEquals(0, ktorApi.calledCount)
            expectNoEvents()

            viewModel.refreshBreeds()
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(breedViewStateSuccessNoFavorite.copy(isLoading = true))
            )
            assertEquals(1, ktorApi.calledCount)
        }
    }

    @Test
    fun `Display API error on first run`() = runTest {
        ktorApi.throwOnCall(RuntimeException("Test error"))

        viewModel.breedState.test {
            assertEquals(
                BreedViewState.Error(error = "Unable to download breed list"),
                awaitItemPrecededBy(BreedViewState.Initial, BreedViewState.Empty())
            )
        }
    }

    @Test
    fun `Ignore API error with cache`() = runTest {
        dbHelper.insertBreeds(breedNames)
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            (clock.currentInstant - 2.hours).toEpochMilliseconds()
        )
        ktorApi.throwOnCall(RuntimeException("Test error"))

        viewModel.breedState.test {
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(BreedViewState.Initial)
            )
            expectNoEvents()

            ktorApi.prepareResult(ktorApi.successResult())
            viewModel.refreshBreeds()

            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(breedViewStateSuccessNoFavorite.copy(isLoading = true))
            )
        }
    }

    @Test
    fun `Ignore API error on refresh with cache`() = runTest {
        ktorApi.prepareResult(ktorApi.successResult())

        viewModel.breedState.test {
            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(BreedViewState.Initial, BreedViewState.Empty())
            )
            expectNoEvents()

            ktorApi.throwOnCall(RuntimeException("Test error"))
            viewModel.refreshBreeds()

            assertEquals(
                breedViewStateSuccessNoFavorite,
                awaitItemPrecededBy(breedViewStateSuccessNoFavorite.copy(isLoading = true))
            )
        }
    }

    @Test
    fun `Show API error on refresh without cache`() = runTest {
        settings.putLong(
            BreedRepository.DB_TIMESTAMP_KEY,
            clock.currentInstant.toEpochMilliseconds()
        )
        ktorApi.throwOnCall(RuntimeException("Test error"))

        viewModel.breedState.test {
            assertEquals(BreedViewState.Empty(), awaitItemPrecededBy(BreedViewState.Initial))
            expectNoEvents()

            viewModel.refreshBreeds()
            assertEquals(
                BreedViewState.Error(error = "Unable to refresh breed list"),
                awaitItemPrecededBy(BreedViewState.Empty(isLoading = true))
            )
        }
    }
}

// There's a race condition where intermediate states can get missed if the next state comes too fast.
// This function addresses that by awaiting an item that may or may not be preceded by the specified other items
private suspend fun ReceiveTurbine<BreedViewState>.awaitItemPrecededBy(
    vararg items: BreedViewState
): BreedViewState {
    var nextItem = awaitItem()
    for (item in items) {
        if (item == nextItem) {
            nextItem = awaitItem()
        }
    }
    return nextItem
}
