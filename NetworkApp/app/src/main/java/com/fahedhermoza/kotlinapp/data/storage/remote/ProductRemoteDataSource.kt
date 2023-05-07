package com.fahedhermoza.kotlinapp.data.storage.remote

import com.fahedhermoza.kotlinapp.data.storage.ProductDataSource
import com.fahedhermoza.kotlinapp.data.storage.StorageResult
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductRemoteDataSource : ProductDataSource, KoinComponent {

    private val serviceApi: ProductApiClient by inject()

    override suspend fun products(token: String?): List<ProductDTO> {
        val map: MutableMap<String, String> = mutableMapOf()
        token?.let {
            map["user-token"] = it
        }
        return serviceApi.products(
            ProductConstant.APPLICATION_ID,
            ProductConstant.REST_API_KEY,
            map
        )
    }

    override suspend fun save(token: String?, product: ProductDTO): ProductDTO {
        val map: MutableMap<String, String> = mutableMapOf()
        token?.let {
            map["user-token"] = it
        }
        val raw =
            ProductRaw(product.name, product.description, product.cost, product.logo, product.code)
        val response = serviceApi.addProduct(
            ProductConstant.APPLICATION_ID,
            ProductConstant.REST_API_KEY,
            map,
            raw
        )

        return ProductDTO(
            response.objectId, response.name, response.description,
            response.cost, response.logo, response.code
        )
    }

    override suspend fun update(token: String?, product: ProductDTO): ProductDTO {
        val map: MutableMap<String, String> = mutableMapOf()
        token?.let {
            map["user-token"] = it
        }
        val raw =
            ProductRaw(product.name, product.description, product.cost, product.logo, product.code)
        val response = serviceApi.updateProduct(
            ProductConstant.APPLICATION_ID,
            ProductConstant.REST_API_KEY, map, product.objectId, raw
        )

        return ProductDTO(
            response.objectId, response.name, response.description,
            response.cost, response.logo, response.code
        )
    }

    override suspend fun delete(token: String?, product: ProductDTO): StorageResult<DeleteDTO> {
        val map: MutableMap<String, String> = mutableMapOf()
        token?.let {
            map["user-token"] = it
        }
        return try {
            val response = serviceApi.deleteProduct(
                ProductConstant.APPLICATION_ID,
                ProductConstant.REST_API_KEY,
                map,
                product.objectId
            )

            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    StorageResult.Complete(DeleteDTO(data?.deletionTime))
                } else {
                    StorageResult.Failure(Exception(it.errorBody()?.string()))
                }
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

    override suspend fun deleteAll(token: String?, minimalCost: Double?): Int {
        val map: MutableMap<String, String> = mutableMapOf()
        token?.let {
            map["user-token"] = it
        }
        var where: String = "cost > $minimalCost"
        return serviceApi.deleteProducts(
            ProductConstant.APPLICATION_ID,
            ProductConstant.REST_API_KEY,
            map,
            where
        )
    }
}