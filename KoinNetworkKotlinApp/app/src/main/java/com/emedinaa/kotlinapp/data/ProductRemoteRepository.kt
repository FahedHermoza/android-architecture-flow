package com.emedinaa.kotlinapp.data

import com.emedinaa.kotlinapp.data.storage.Mapper
import com.emedinaa.kotlinapp.data.storage.ProductDataSource
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Delete
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRemoteRepository (private val dataSource: ProductDataSource): ProductRepository{


    override suspend fun fetch(token: String): StorageResult<Product> = withContext(Dispatchers.IO) {
        when (val result = dataSource.products(token)) {
            is StorageResult.Success ->
                StorageResult.Success(result.data?.map {
                    Mapper.productDTOToProduct(it)
                })
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }

    override suspend fun add(token: String?, product: Product): StorageResult<Product> = withContext(Dispatchers.IO) {
        when (val result = dataSource.save(token, Mapper.productToProductDTO(product))) {
            is StorageResult.Complete ->
                StorageResult.Complete(result.data?.let {
                    Mapper.productDTOToProduct(it)
                })
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }

    override suspend fun update(token: String?, product: Product): StorageResult<Product> = withContext(Dispatchers.IO) {
        when (val result = dataSource.update(token, Mapper.productToProductDTO(product))) {
            is StorageResult.Complete ->
                StorageResult.Complete(result.data?.let {
                    Mapper.productDTOToProduct(it)
                })
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }

    override suspend fun delete(token: String?, product: Product): StorageResult<Delete> = withContext(Dispatchers.IO) {
        when (val result = dataSource.delete(token,Mapper.productToProductDTO(product))) {
            is StorageResult.Complete -> StorageResult.Complete(
                   Delete(result.data?.deletionTime ?: 0.toLong())
            )
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }

    override suspend fun clear(token: String?, minimalCost: Double?): StorageResult<MultipleDelete> = withContext(Dispatchers.IO) {
        when (val result = dataSource.deleteAll(token,minimalCost)) {
            is StorageResult.Complete -> StorageResult.Complete(
                    MultipleDelete(result.data?:0)
            )
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }
}