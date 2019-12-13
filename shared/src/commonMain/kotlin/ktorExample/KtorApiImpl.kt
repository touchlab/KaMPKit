package com.touchlab.shared.ktorExample

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.Parameters
import io.ktor.http.takeFrom
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
object KtorApiImpl : KtorApiInterface {
    private val client = HttpClient()

    override suspend fun getThingJson(): String = client.get{
        dogs("api/breeds/image/random")
    }

    override suspend fun setThingJson(value: String): Boolean = client.submitForm(formParameters = Parameters.build {
        append("value",value)
    }, block = {
        dogs("")
    })

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }
}