package com.fahed.composeapp.domain

import com.fahed.composeapp.core.data.DataState
import com.fahed.composeapp.data.storage.StorageResult
import com.fahed.composeapp.domain.model.Delete
import com.fahed.composeapp.domain.model.MultipleDelete
import com.fahed.composeapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun fetch(token: String): Flow<DataState<List<Product>>>
    suspend fun add(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun update(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun delete(token: String?, product: Product): StorageResult<Delete>
    suspend fun clear(token: String?, minimalCost: Double?): Flow<DataState<MultipleDelete>>
}