package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.sqldelight.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.inject

class ItemModel(private val viewUpdate:(ItemDataSummary)->Unit): BaseModel(){
    private val dbHelper: DatabaseHelper by inject()

    init {
        mainScope.launch {
            dbHelper.selectAllItems().asFlow()
                .map {q ->
                    val itemList = q.executeAsList()
                    ItemDataSummary(itemList.maxBy { it.value.length }, itemList)
                }
                .flowOn(Dispatchers.Default)
                .collect { summary ->
                    viewUpdate(summary)
                }
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

data class ItemDataSummary(val longestItem:Items?, val allItems:List<Items>)