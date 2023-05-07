package com.emedinaa.kotlinapp.data.storage

/**
 * @author Eduardo Medina
 */
sealed class StorageResult<out T> {
    data class Success<T>(val data: List<T>) : StorageResult<T>()
    data class Complete<T>(val data: T?) : StorageResult<T>()
    data class Failure(val exception: Exception) : StorageResult<Nothing>()
}
