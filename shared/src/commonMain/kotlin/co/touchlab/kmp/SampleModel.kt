package co.touchlab.kmp

import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch

class SampleModel : BaseModel(){
    lateinit var settings: Settings

    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = KtorApiImpl.getJsonFromApi()
            onResult(result)
        }
    }

    fun initSettings(platformSettings: Settings){
        settings = platformSettings
        settings.putBoolean("TEMP",true)
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")
}