package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.ktor.KtorDogApiImpl
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import org.koin.core.inject

class SampleModel : BaseModel(){
    private val settings: Settings by inject()

    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = KtorDogApiImpl.getJsonFromApi()
            onResult(result)
        }
    }

    fun initSettings(){
        settings.putBoolean("TEMP",true)
    }

    fun getBooleanSetting(): Boolean = settings.getBoolean("TEMP")
}