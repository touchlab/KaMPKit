package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.base.ApiResult
import co.touchlab.kampkit.base.ApiResult.Companion.toResult
import co.touchlab.kampkit.response.BreedResult
import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import co.touchlab.kermit.Logger as KermitLogger

class Api(private val log: KermitLogger, private val client: HttpClient) {

    init {
        ensureNeverFrozen()
    }

    private val baseUrl = "https://dog.ceo/"

    suspend fun getBreeds(): ApiResult<BreedResult> {
        return fetch("breed") {
            client.get {
                base("api/breeds/list/all")
            }
        }
    }

    private fun HttpRequestBuilder.base(path: String) {
        url {
            takeFrom(baseUrl)
            encodedPath = path
        }
    }

    private suspend inline fun <reified T> fetch(type: String, request: () -> HttpResponse): ApiResult<T> {
        log.d { "Fetching $type from network" }
        val result: ApiResult<T> = request().toResult()

        if (result is ApiResult.Error) {
            log.e { "Fetching $type failed. ${result.errorMessage}" }
        }

        return result
    }

}
