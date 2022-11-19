package co.touchlab.kampkit.feature.breed

import co.touchlab.kampkit.db.Breed
import kotlinx.coroutines.flow.Flow

interface BreedRepository {

    fun getData(): Flow<BreedRepoData>

    suspend fun refreshDataIfStale()

    suspend fun refreshData()

    suspend fun updateBreedFavorite(breed: Breed)

}
