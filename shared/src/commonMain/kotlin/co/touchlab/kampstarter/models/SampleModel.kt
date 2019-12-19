package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.ktor.KtorDogApiImpl
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch

class SampleModel : BaseModel(){
    lateinit var settings: Settings

    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = KtorDogApiImpl.getJsonFromApi()
            onResult(result)
        }
    }

    fun initSettings(platformSettings: Settings){
        settings = platformSettings
        settings.putBoolean("TEMP",true)
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")
}