package com.emedinaa.kotlinapp.data

import com.emedinaa.kotlinapp.core.data.DataResponseHandler
import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.data.safeApiCall
import com.emedinaa.kotlinapp.data.storage.Mapper
import com.emedinaa.kotlinapp.data.storage.ProductDataSource
import com.emedinaa.kotlinapp.data.storage.StorageResult
import com.emedinaa.kotlinapp.data.storage.remote.ProductDTO
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Delete
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ProductRemoteRepository(private val dataSource: ProductDataSource) : ProductRepository {

    override suspend fun fetch(token: String): Flow<DataState<List<Product>>> = flow {
        // 1. emit a 'loading event'
        emit(DataState.loading(true))

        // 2. apiCall / cacheCall
        val apiCall = safeApiCall { dataSource.products(token) }

        // 3. process '..call' and emit it
        emit(
            object : DataResponseHandler<List<Product>, List<ProductDTO>>(response = apiCall) {
                override suspend fun handleSuccess(resultObj: List<ProductDTO>): DataState<List<Product>> {

                    return if (!resultObj.isNullOrEmpty()) {
                        var listProduct: ArrayList<Product> = arrayListOf()
                        for (element in resultObj) {
                            listProduct.add(Mapper.productDTOToProduct(element))
                        }
                        DataState.data(message = "message", data = listProduct)
                    } else if (resultObj.isEmpty()) {
                        DataState.data(message = "message", data = emptyList())
                    } else DataState.error(code = 200, message = "message")
                }
            }.getResult()
        )
    }

    override suspend fun add(token: String?, product: Product): Flow<DataState<Product>> = flow {
        emit(DataState.loading(true))
        val apiCall = safeApiCall { dataSource.save(token, Mapper.productToProductDTO(product)) }

        emit(
            object : DataResponseHandler<Product, ProductDTO>(response = apiCall) {
                override suspend fun handleSuccess(resultObj: ProductDTO): DataState<Product> {
                    return if (resultObj.objectId != "") {
                        DataState.data(
                            message = "message",
                            data = Mapper.productDTOToProduct(resultObj)
                        )
                    } else DataState.error(code = 200, message = "message")
                }
            }.getResult()
        )
    }

    override suspend fun update(token: String?, product: Product): Flow<DataState<Product>> = flow {
        emit(DataState.loading(true))
        val apiCall = safeApiCall { dataSource.update(token, Mapper.productToProductDTO(product)) }

        emit(
            object : DataResponseHandler<Product, ProductDTO>(response = apiCall) {
                override suspend fun handleSuccess(resultObj: ProductDTO): DataState<Product> {
                    return if (resultObj.objectId != "") {
                        DataState.data(
                            message = "message",
                            data = Mapper.productDTOToProduct(resultObj)
                        )
                    } else DataState.error(code = 200, message = "message")
                }
            }.getResult()
        )
    }

    override suspend fun delete(token: String?, product: Product): StorageResult<Delete> =
        withContext(Dispatchers.IO) {
            when (val result = dataSource.delete(token, Mapper.productToProductDTO(product))) {
                is StorageResult.Complete -> StorageResult.Complete(
                    Delete(result.data?.deletionTime ?: 0.toLong())
                )
                is StorageResult.Failure -> StorageResult.Failure(result.exception)
                else -> StorageResult.UnAuthorized(Exception())
            }
        }

    override suspend fun clear(
        token: String?,
        minimalCost: Double?
    ): Flow<DataState<MultipleDelete>> = flow {
        emit(DataState.loading(true))
        val apiCall = safeApiCall { dataSource.deleteAll(token, minimalCost) }

        emit(
            object : DataResponseHandler<MultipleDelete, Int>(response = apiCall) {
                override suspend fun handleSuccess(resultObj: Int): DataState<MultipleDelete> {
                    return if (resultObj > 0) {
                        DataState.data(message = "message", data = MultipleDelete(resultObj))
                    } else DataState.error(code = 200, message = "message")
                }
            }.getResult()
        )
    }
}