package com.emedinaa.kotlinapp.data

import com.emedinaa.kotlinapp.data.storage.ProductDataSource
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductDatabaseRepository(private val productDataSource: ProductDataSource) :
    ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> {
        return productDataSource.notes().map { itListDbProduct ->
            Mapper.mapDBProductListToProductList(itListDbProduct)
        }
    }

    override suspend fun addProduct(product: Product) {
        productDataSource.addNote(Mapper.productToDbProduct(product))
    }

    override suspend fun updateProduct(product: Product) {
        productDataSource.updateNote(Mapper.productToDbProduct(product))
    }

    override suspend fun deleteProduct(product: Product) {
        productDataSource.deleteNote(Mapper.productToDbProduct(product))
    }

    override suspend fun deleteAllProduct() {
        productDataSource.deleteAll()
    }
}