package com.fahed.composeapp.data

import com.fahed.composeapp.data.storage.ProductDataSource
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductDatabaseRepository(private val productDataSource: ProductDataSource) :
    ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> {
        return productDataSource.notes().map { itListDbProduct ->
            Mapper.mapDBProductListToProductList(itListDbProduct)
        }
    }

    override suspend fun getProduct(id: Int): Product {
        return Mapper.dbProductToProduct(productDataSource.getNote(id))
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