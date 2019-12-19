package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.db.KampstarterDb
import co.touchlab.kampstarter.ktor.KtorApi
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.launch

class SampleModel(private val ktorApiImpl: KtorApi,
                  private val sqlDriver: SqlDriver,
                  private val settings:Settings ) : BaseModel(){

    val dbHelper = DatabaseHelper(sqlDriver)

    init {
        // TEMP
        settings.putBoolean("TEMP",true)
        dbHelper.insertItem(1,"Test")
        dbHelper.insertItem(2,"Test2")
    }

    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = ktorApiImpl.getJsonFromApi()
            onResult(result)
        }
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")

    fun getDatabaseRows() = dbHelper.selectAllItems().executeAsList()
}