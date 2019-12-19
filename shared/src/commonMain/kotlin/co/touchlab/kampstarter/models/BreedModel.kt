package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.ktor.KtorDogApiImpl
import co.touchlab.kampstarter.sqldelight.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import kotlinx.coroutines.withContext
import org.koin.core.inject

class BreedModel(private val viewUpdate:(ItemDataSummary)->Unit): BaseModel(){
    private val dbHelper: DatabaseHelper by inject()

    init {
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

    fun getBreedsFromNetwork(onResult:(String)->Unit) {
        mainScope.launch {
            val result = KtorDogApiImpl.getJsonFromApi()
            val speakers = Json.nonstrict.parse(co.touchlab.kampstarter.jsondata.Breed.serializer().list, result)

            onResult(result)
        }
    }

    fun insertSomeData(){
        mainScope.launch {
            withContext(Dispatchers.Default){
                //This should all be in a transaction
                dbHelper.deleteAll()
                dbHelper.insertItem(1, "Hello 1")
                dbHelper.insertItem(2, "Hello 2")
            }
        }
    }
}

data class ItemDataSummary(val longestItem:Breed?, val allItems:List<Breed>)
