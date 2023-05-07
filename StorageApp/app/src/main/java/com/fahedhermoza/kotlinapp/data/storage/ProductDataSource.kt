package com.fahedhermoza.kotlinapp.data.storage

import com.fahedhermoza.kotlinapp.data.storage.db.DBProduct
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {

    fun notes(): Flow<List<DBProduct>>
    suspend fun addNote(product: DBProduct)
    suspend fun updateNote(product: DBProduct)
    suspend fun deleteNote(product: DBProduct)
    suspend fun deleteAll()
}