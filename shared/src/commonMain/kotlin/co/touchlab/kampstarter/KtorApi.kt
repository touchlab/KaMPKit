package co.touchlab.kampstarter

interface KtorApi {
    suspend fun getJsonFromApi():String
    suspend fun setThingJson(value: String):Boolean
}