package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.ktor.KtorApi
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch

class SampleModel(private val ktorApiImpl: KtorApi) : BaseModel(){
    lateinit var settings: Settings

    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = ktorApiImpl.getJsonFromApi()
            onResult(result)
        }
    }
    fun initSettings(platformSettings: Settings){
        settings = platformSettings
        settings.putBoolean("TEMP",true)
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")
}