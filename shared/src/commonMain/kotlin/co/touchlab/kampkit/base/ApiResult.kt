package co.touchlab.kampkit.base

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed class ApiResult<T> {

    data class Success<T>(
        val data: T
    ) : ApiResult<T>()

    data class Error<T>(val errorMessage: String) : ApiResult<T>()

    companion object {

        suspend inline fun <reified T> HttpResponse.toResult(): ApiResult<T> {
            return try {
                val data: T = body()
                Success(data)
            } catch (e: Exception) {
                Error(e.toString())
            }

        }
    }
}