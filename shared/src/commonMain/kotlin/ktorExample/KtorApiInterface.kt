package com.touchlab.shared.ktorExample

interface KtorApiInterface {
    fun getJsonFromApi(callback: (String) -> Unit)
     fun setThingJson(value: String, callback: (Boolean) -> Unit)
}