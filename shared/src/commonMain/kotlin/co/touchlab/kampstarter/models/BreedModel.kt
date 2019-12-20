package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.ktor.DogApiImpl
import co.touchlab.kampstarter.sqldelight.asFlow
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.inject

@ExperimentalCoroutinesApi
class BreedModel(private val viewUpdate:(ItemDataSummary)->Unit): BaseModel(){
    private val dbHelper: DatabaseHelper by inject()
    private val settings: Settings by inject()

    private val DB_TIMESTAMP_KEY = "DbTimestampKey"

    init {
        ensureNeverFrozen()
        mainScope.launch {
            dbHelper.selectAllItems().asFlow()
                .map {q ->
                    val itemList = q.executeAsList()
                    ItemDataSummary(itemList.maxBy { it.name.length }, itemList)
                }
                .flowOn(Dispatchers.Default)
                .collect { summary ->
                    viewUpdate(summary)
                }
        }
    }

    fun isBreedListStale(currentTimeMS:Long) : Boolean{
        val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
        val oneHourMS = 60 * 60 * 1000
        return (lastDownloadTimeMS + oneHourMS < currentTimeMS)
    }

    fun getBreedsFromNetwork(currentTimeMS:Long) {
        if(isBreedListStale(currentTimeMS)) {
            mainScope.launch {
                val breedResult = DogApiImpl.getJsonFromApi()
                val breedList = breedResult.message.keys.toList()
                insertBreedData(breedList)
                settings.putLong(DB_TIMESTAMP_KEY, currentTimeMS)
            }
        }
    }

    private fun insertBreedData(breeds: List<String>){
        breeds.forEach {  breed ->
            dbHelper.insertBreed(breed)
        }
    }

    fun updateBreedFavorite(breedId:Long, favorite: Boolean){
        mainScope.launch {
            dbHelper.updateFavorite(breedId, favorite)
        }
    }
/*
    suspend fun insertBreedData(){
        mainScope.launch {
            withContext(Dispatchers.Default){
                //This should all be in a transaction
                dbHelper.deleteAll()
                dbHelper.insertItem(1, "Hello 1")
                dbHelper.insertItem(2, "Hello 2")
            }
        }
    }
    */
}

data class ItemDataSummary(val longestItem:Breed?, val allItems:List<Breed>)
