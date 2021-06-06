package com.emedinaa.kotlinapp.core.data

import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

private const val NETWORK_TIMEOUT = 60
private const val CACHE_TIMEOUT = 5

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val code: Int? = null, val message: String? = null) : DataResult<Nothing>()
    object NetworkError : DataResult<Nothing>()
}

suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T?
): DataResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT * 1000L) {
                DataResult.Success(apiCall.invoke())
            }
        } catch (ex: Exception) {
            Timber.e("Error: $ex")
            when (ex) {
                is TimeoutCancellationException -> DataResult.Error(408)
                is IOException -> DataResult.NetworkError
                is HttpException -> DataResult.Error(ex.code())
                else -> DataResult.Error(null, "Unknown error")
            }
        }
    }
}

suspend fun <T : Any> safeCacheCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    cacheCall: suspend () -> T?
): DataResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT * 1000L) {
                DataResult.Success(cacheCall.invoke())
            }
        } catch (ex: Exception) {
            when (ex) {
                is TimeoutCancellationException -> DataResult.Error(408)
                else -> DataResult.Error(null, "Unknown error")
            }
        }
    }
}