package com.touchlab.shared.ktorExample

import com.touchlab.shared.MainDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.io.core.use
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object KtorApiImpl : KtorApiInterface {
    private val client = HttpClient()

    override fun getJsonFromApi(callback: (String) -> Unit) {
        GlobalScope.launch(MainDispatcher) {
            val result = client.get<String>{
                dogs("api/breeds/image/random")
            }
            callback(result)
        }
    }

    override fun setThingJson(value: String, callback: (Boolean) -> Unit) {
        GlobalScope.launch(MainDispatcher) {
            client.submitForm<HttpResponse>(
                formParameters = Parameters.build {
                    append("value", value)
                }, block = {
                    dogs("")
            }).use {
                it.status.isSuccess()
            }
        }
    }

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }

}