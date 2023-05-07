package com.fahedhermoza.kotlinapp.domain

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.data.storage.StorageResult
import com.fahedhermoza.kotlinapp.domain.model.Delete
import com.fahedhermoza.kotlinapp.domain.model.MultipleDelete
import com.fahedhermoza.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun fetch(token: String): Flow<DataState<List<Product>>>
    suspend fun add(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun update(token: String?, product: Product): Flow<DataState<Product>>
    suspend fun delete(token: String?, product: Product): StorageResult<Delete>
    suspend fun clear(token: String?, minimalCost: Double?): Flow<DataState<MultipleDelete>>
}