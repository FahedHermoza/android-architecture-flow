package com.emedinaa.kotlinapp.domain

import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.Delete
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product

interface ProductRepository {

    suspend fun fetch(token:String): StorageResult<Product>
    suspend fun add(token: String?, product: Product): StorageResult<Product>
    suspend fun update(token: String?, product: Product): StorageResult<Product>
    suspend fun delete(token: String?, product: Product): StorageResult<Delete>
    suspend fun clear(token: String?, minimalCost: Double?): StorageResult<MultipleDelete>
}