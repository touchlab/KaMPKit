package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.ktor.DogApiImpl
import co.touchlab.kampstarter.response.BreedResult
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.launch
import org.koin.core.inject

class SampleModel : BaseModel(){
    private val settings: Settings by inject()

    fun performNetworkRequest(onResult:(BreedResult)->Unit) {
        mainScope.launch {
            val result = DogApiImpl.getJsonFromApi()
            onResult(result)
        }
    }

    fun initSettings(){
        settings.putBoolean("TEMP",true)
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")

    fun getDatabaseRows() = dbHelper.selectAllItems().executeAsList()
}
