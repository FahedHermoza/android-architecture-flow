package com.emedinaa.kotlinapp.core.data

data class DataState<T>(
    val data: T? = null,
    val loading: Boolean = false,
    val message: String? = null,
    val code: Int? = null,
    val type: DataType = DataType.None
) {
    companion object {
        fun <T> loading(isLoading: Boolean): DataState<T> =
            DataState(loading = isLoading)

        fun <T> data(message: String? = null, data: T? = null): DataState<T> =
            DataState(message = message, data = data, type = DataType.Success)

        fun <T> error(code: Int? = null, message: String? = null): DataState<T> =
            DataState(code = code, message = message, type = DataType.Error)
    }
}

sealed class DataType {
    object Success : DataType()
    object Error : DataType()
    object None : DataType()
}
