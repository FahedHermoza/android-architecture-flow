package com.fahed.composeapp.data.storage

import androidx.lifecycle.LiveData
import com.fahed.composeapp.data.storage.db.DBProduct
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {

    fun notes(): Flow<List<DBProduct>>
    suspend fun getNote(id: Int): DBProduct
    suspend fun addNote(product: DBProduct)
    suspend fun updateNote(product: DBProduct)
    suspend fun deleteNote(product: DBProduct)
    suspend fun deleteAll()
}