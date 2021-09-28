package com.emedinaa.kotlinapp.domain

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.data.storage.StorageResult
import com.emedinaa.kotlinapp.domain.model.Delete
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun fetch(token: String): Flow<DataState<List<Product>>>
    suspend fun add(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun update(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun delete(token: String?, product: Product): StorageResult<Delete>
    suspend fun clear(token: String?, minimalCost: Double?): Flow<DataState<MultipleDelete>>
}