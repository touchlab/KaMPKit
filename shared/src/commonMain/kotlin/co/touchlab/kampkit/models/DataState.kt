package co.touchlab.kampkit.models

sealed class DataState<out T>(open val loading: Boolean) {
    class Success<T>(val data: T, override val loading: Boolean = false) : DataState<T>(loading) {
        override fun copy(isLoading: Boolean): DataState<T> = Success(data, isLoading)
        override fun equals(other: Any?) = other is Success<*> && other.data == data && other.loading == loading
    }

    class Error(val exception: String, override val loading: Boolean = false) : DataState<Nothing>(loading) {
        override fun copy(isLoading: Boolean): DataState<Nothing> = Error(exception, isLoading)
        override fun equals(other: Any?) = other is Error && other.exception == exception && other.loading == loading
    }

    class Empty(override val loading: Boolean = false) : DataState<Nothing>(loading) {
        override fun copy(isLoading: Boolean) = this
        override fun equals(other: Any?) = other is Empty && other.loading == loading
    }

    object Loading : DataState<Nothing>(true) {
        override fun copy(isLoading: Boolean): DataState<Nothing> = this
    }

    abstract fun copy(isLoading: Boolean): DataState<T>
}
