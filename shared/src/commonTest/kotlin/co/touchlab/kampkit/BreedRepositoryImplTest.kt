package co.touchlab.kampkit

import co.touchlab.kampkit.base.StaleDataDelegate
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.ktor.Api
import co.touchlab.kampkit.models.BreedRepositoryImpl
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class BreedRepositoryImplTest : TestsWithMocks() {
    override fun setUpMocks() = injectMocks(mocker)

    @Mock
    lateinit var staleDataDelegate: StaleDataDelegate

    @Mock
    lateinit var api: Api

    private val kermit = Logger(StaticConfig())

    private val testDbConnection = testDbConnection()
    private val dbHelper = DatabaseHelper(
        kermit,
        testDbConnection,
        Dispatchers.Default
    )

    private lateinit var ut: BreedRepositoryImpl

    companion object {
        private val appenzeller = Breed(1, "appenzeller", false)
        private val australianNoLike = Breed(2, "australian", false)
        private val australianLike = Breed(2, "australian", true)
        private val breedsNoFavorite = listOf(appenzeller, australianNoLike)
        private val breedsFavorite = listOf(appenzeller, australianLike)
        private val breedNames = breedsFavorite.map { it.name }
    }

    @BeforeTest
    fun init() {
        ut = BreedRepositoryImpl(
            kermit, dbHelper, api, staleDataDelegate
        )
    }

    @AfterTest
    fun tearDown() = runTest {
        testDbConnection.close()
    }

    @Test
    fun `Toggle favorite cached breed`() = runTest {
        dbHelper.insertBreeds(breedNames)
        dbHelper.updateFavorite(australianLike.id, true)
        //
        // repository.getData().test {
        //     assertEquals(breedsFavorite, awaitItem())
        //     expectNoEvents()
        //
        //     repository.updateBreedFavorite(australianLike)
        //     assertEquals(breedsNoFavorite, awaitItem())
        // }
    }

}
