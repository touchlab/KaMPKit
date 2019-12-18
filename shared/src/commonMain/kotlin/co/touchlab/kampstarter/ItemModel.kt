package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Items

class ItemModel(private val viewUpdate:(ItemDataSummary)->Unit): BaseModel(){
    init {

        ServiceRegistry.helper.selectAllItems().asFlowWithTransform { q ->
            val itemList = q.executeAsList()
            ItemDataSummary(itemList.maxBy { it.value.length }, itemList)
        }
    }
}

data class ItemDataSummary(val longestItem:Items?, val allItems:List<Items>)