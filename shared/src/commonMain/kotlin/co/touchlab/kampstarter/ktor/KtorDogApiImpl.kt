package co.touchlab.kampstarter.ktor

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import kotlinx.io.core.use
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object KtorDogApiImpl : KtorApi {
    private val client = HttpClient()

    override suspend fun getJsonFromApi(): String =
        client.get<String> {
            dogs("api/breeds/list/all")
        }


    override suspend fun setThingJson(value: String): Boolean = client.submitForm<HttpResponse>(
        formParameters = Parameters.build {
            append("value", value)
        }, block = {
            dogs("")
        }).use {
        it.status.isSuccess()
    }

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }
}