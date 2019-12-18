package co.touchlab.kmp

import kotlinx.coroutines.launch

class SampleModel : BaseModel(){
    fun performNetworkRequest(onResult:(String)->Unit) {
        mainScope.launch {
            val result = KtorApiImpl.getJsonFromApi()
            onResult(result)
        }
    }
}