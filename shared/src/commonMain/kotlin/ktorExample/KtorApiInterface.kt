package com.touchlab.shared.ktorExample

interface KtorApiInterface {
    suspend fun getThingJson(): String
    suspend fun setThingJson(value: String): Boolean
}