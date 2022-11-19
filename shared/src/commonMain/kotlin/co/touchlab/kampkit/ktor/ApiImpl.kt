package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.base.ApiStatus
import co.touchlab.kampkit.base.ApiStatus.Companion.toResult
import co.touchlab.kampkit.ktor.dto.BreedDto
import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import co.touchlab.kermit.Logger as KermitLogger

class ApiImpl(private val log: KermitLogger, private val client: HttpClient): Api {

    init {
        ensureNeverFrozen()
    }

    private val baseUrl = "https://dog.ceo/"

    override suspend fun getBreeds(): ApiStatus<BreedDto> {
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

    private suspend inline fun <reified T> fetch(
        type: String,
        request: () -> HttpResponse
    ): ApiStatus<T> {
        log.d { "Fetching $type from network" }

        return request().toResult()
    }
}
