package com.emedinaa.kotlinapp.core.data

abstract class DataResponseHandler<ViewState, Data>(private val response: DataResult<Data?>) {

    suspend fun getResult(): DataState<ViewState> {
        return when (response) {
            is DataResult.Success -> {
                if (response.data == null) DataState.error(message = "Data is NULL")
                else handleSuccess(resultObj = response.data)
            }

            is DataResult.Error -> DataState.error(code = response.code, message = response.message ?:"")

            is DataResult.NetworkError -> DataState.error(message = "General network error")
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>

}