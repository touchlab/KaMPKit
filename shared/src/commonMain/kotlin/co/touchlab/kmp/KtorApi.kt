package co.touchlab.kmp

interface KtorApi {
    suspend fun getJsonFromApi():String
    suspend fun setThingJson(value: String):Boolean
}