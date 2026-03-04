package co.touchlab.kampkit.models

import co.touchlab.kampkit.db.Breed
import kotlinx.coroutines.flow.Flow

/**
 * 与 BreedRepository 同构的接口，便于 Harmony/JS 等平台用网络+内存实现（无需 SqlDelight）。
 * 华为 Common + Platform Main 思路下，各端可提供不同实现。
 */
interface IBreedRepository {
    fun getBreeds(): Flow<List<Breed>>
    suspend fun refreshBreedsIfStale()
    suspend fun refreshBreeds()
    suspend fun updateBreedFavorite(breed: Breed)
}
