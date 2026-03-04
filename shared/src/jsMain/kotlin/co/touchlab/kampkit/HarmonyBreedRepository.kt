package co.touchlab.kampkit

import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.IBreedRepository
import co.touchlab.kampkit.ktor.DogApi
import co.touchlab.kermit.Logger
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock

/**
 * Harmony/JS 端 Breed 数据源：网络 + 内存，与 commonMain BreedRepository 同构 API。
 * 不依赖 SqlDelight，符合华为 Common + Platform Main 思路。
 */
class HarmonyBreedRepository(
    private val settings: Settings,
    private val dogApi: DogApi,
    log: Logger,
    private val clock: kotlin.time.Clock,
) : IBreedRepository {

    private val log = log.withTag("BreedModel")
    private val mutex = Mutex()
    private val cache = MutableStateFlow<List<Breed>>(emptyList())

    companion object {
        private const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    override fun getBreeds(): Flow<List<Breed>> = cache

    override suspend fun refreshBreedsIfStale() {
        if (isBreedListStale()) {
            refreshBreeds()
        }
    }

    override suspend fun refreshBreeds() {
        val result = dogApi.getJsonFromApi()
        log.v { "Breed network result: ${result.status}" }
        val names = result.message.keys.sorted()
        log.v { "Fetched ${names.size} breeds from network" }
        settings.putLong(DB_TIMESTAMP_KEY, clock.now().toEpochMilliseconds())
        mutex.withLock {
            val nextId = (cache.value.maxOfOrNull { it.id } ?: 0L) + 1
            val existing = cache.value.associateBy { it.name }
            val list = names.mapIndexed { index, name ->
                existing[name] ?: Breed(
                    id = nextId + index,
                    name = name,
                    favorite = false,
                )
            }
            cache.value = list
        }
    }

    override suspend fun updateBreedFavorite(breed: Breed) {
        mutex.withLock {
            cache.value = cache.value.map {
                if (it.id == breed.id) Breed(it.id, it.name, !it.favorite) else it
            }
        }
    }

    private fun isBreedListStale(): Boolean {
        val last = settings.getLong(DB_TIMESTAMP_KEY, 0)
        val oneHour = 60 * 60 * 1000
        return last + oneHour < clock.now().toEpochMilliseconds()
    }
}
