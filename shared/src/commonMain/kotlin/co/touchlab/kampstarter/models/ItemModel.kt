package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.ServiceRegistry
import co.touchlab.kampstarter.asFlowWithTransform
import co.touchlab.kampstarter.db.Items
import co.touchlab.kmp.sqldelight.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.inject

class ItemModel(private val viewUpdate:(ItemDataSummary)->Unit): BaseModel(){
    private val dbHelper:DatabaseHelper by inject()

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
}

data class ItemDataSummary(val longestItem:Items?, val allItems:List<Items>)