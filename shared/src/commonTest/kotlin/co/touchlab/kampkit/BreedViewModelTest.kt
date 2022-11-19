package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.feature.breed.BreedRepository
import co.touchlab.kampkit.feature.breed.BreedViewModel
import co.touchlab.kampkit.feature.breed.BreedViewState
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class BreedViewModelTest: TestsWithMocks() {
    override fun setUpMocks() = injectMocks(mocker)

    @Mock
    lateinit var repository: BreedRepository

    private var kermit = Logger(StaticConfig())

    private val viewModel by lazy { BreedViewModel(repository, kermit) }

    companion object {
        private val appenzeller = Breed(1, "appenzeller", false)
        private val australianNoLike = Breed(2, "australian", false)
        private val australianLike = Breed(2, "australian", true)
        private val breedViewStateSuccessNoFavorite = BreedViewState(
            breeds = listOf(appenzeller, australianNoLike)
        )
        private val breedViewStateSuccessFavorite = BreedViewState(
            breeds = listOf(appenzeller, australianLike)
        )
        private val breedNames = breedViewStateSuccessNoFavorite.breeds?.map { it.name }.orEmpty()
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

}