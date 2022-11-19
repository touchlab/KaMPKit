package co.touchlab.kampkit.base

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

sealed class ApiStatus<out T> {

    data class Success<T>(
        val data: T
    ) : ApiStatus<T>()

    data class Error(val statusCode: Int, val errorMessage: String) : ApiStatus<Nothing>() {

        override fun toString(): String {
            return "status: $statusCode, description: $errorMessage"
        }
    }

    object NetworkError : ApiStatus<Nothing>() {

        override fun toString(): String {
            return "Network error"
        }
    }

    object Loading : ApiStatus<Nothing>()

    object NoAction : ApiStatus<Nothing>()

    companion object {

        suspend inline fun <reified T> HttpResponse.toResult(): ApiStatus<T> {
            return try {
                val data: T = body()
                Success(data)
            } catch (e: Exception) {
                when (status) {
                    HttpStatusCode.RequestTimeout -> NetworkError
                    else -> Error(status.value, status.description)
                }
            }
        }
    }
}