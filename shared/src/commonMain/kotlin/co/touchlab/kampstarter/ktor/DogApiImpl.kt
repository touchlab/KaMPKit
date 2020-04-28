package co.touchlab.kampstarter.ktor

import co.touchlab.kampstarter.models.childContext
import co.touchlab.kampstarter.models.isMainThread
import co.touchlab.kampstarter.response.BreedResult
import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class DogApiImpl : KtorApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    init {
        ensureNeverFrozen()
    }

    override suspend fun getJsonFromApi(): BreedResult = network {
        client.get<BreedResult> {
            dogs("api/breeds/list/all")
        }
    }

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }
}

suspend fun <R> network(block: suspend () -> R): R = coroutineScope {
    withContext(childContext()) {
        if (!isMainThread) error("Ktor calls must be run in main thread")
        block()
    }
}