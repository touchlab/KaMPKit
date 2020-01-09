package co.touchlab.kampstarter.ktor

import co.touchlab.kampstarter.response.BreedResult
import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import kotlinx.io.core.use

class DogApiImpl : KtorApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    init {
        ensureNeverFrozen()
    }

    override suspend fun getJsonFromApi(): BreedResult =
        client.get<BreedResult> {
            dogs("api/breeds/list/all")
        }

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }
}