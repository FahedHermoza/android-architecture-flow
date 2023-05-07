package com.fahed.composeapp.data.storage.db

import androidx.lifecycle.LiveData
import com.fahed.composeapp.data.storage.ProductDataSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ProductDatabaseDataSource: ProductDataSource, KoinComponent {

    private val productDao: ProductDao by inject()

    override fun notes(): Flow<List<DBProduct>> = productDao.products()

    override suspend fun getNote(id: Int): DBProduct = productDao.getProduct(id)

    override suspend fun addNote(product: DBProduct) {
        productDao.addProduct(product)
    }

    override suspend fun updateNote(product: DBProduct){
        productDao.updateProduct(product)
    }

    override suspend fun deleteNote(product: DBProduct){
        productDao.deleteProduct(product)
    }

    override suspend fun deleteAll() {
        productDao.deleteAll()
    }
}
