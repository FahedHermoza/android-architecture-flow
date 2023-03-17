package com.emedinaa.kotlinapp.domain

import androidx.lifecycle.LiveData
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProduct()
}