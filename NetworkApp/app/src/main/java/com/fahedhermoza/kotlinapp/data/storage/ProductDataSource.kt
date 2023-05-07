package com.fahedhermoza.kotlinapp.data.storage

import com.fahedhermoza.kotlinapp.data.storage.remote.DeleteDTO
import com.fahedhermoza.kotlinapp.data.storage.remote.ProductDTO

interface ProductDataSource {

    suspend fun products(token: String?): List<ProductDTO>
    suspend fun save(token: String?, product: ProductDTO): ProductDTO
    suspend fun update(token: String?, product: ProductDTO): ProductDTO
    suspend fun delete(token: String?, product: ProductDTO): StorageResult<DeleteDTO>
    suspend fun deleteAll(token: String?, minimalCost: Double?): Int
}