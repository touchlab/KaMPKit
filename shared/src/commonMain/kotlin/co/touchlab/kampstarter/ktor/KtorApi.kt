package co.touchlab.kampstarter.ktor

interface KtorApi {
    suspend fun getJsonFromApi():String
    suspend fun setThingJson(value: String):Boolean
}