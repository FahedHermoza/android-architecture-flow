package com.emedinaa.kotlinapp.data.storage

import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.data.remote.DeleteDTO
import com.emedinaa.kotlinapp.data.remote.ProductDTO
import com.emedinaa.kotlinapp.domain.model.MultipleDelete

interface ProductDataSource {

    suspend fun products(token:String?): List<ProductDTO>
    suspend fun save(token:String?,product:ProductDTO): ProductDTO
    suspend fun update(token:String?,product:ProductDTO): ProductDTO
    suspend fun delete(token:String?,product:ProductDTO):StorageResult<DeleteDTO>
    suspend fun deleteAll(token:String?, minimalCost: Double?): Int
}