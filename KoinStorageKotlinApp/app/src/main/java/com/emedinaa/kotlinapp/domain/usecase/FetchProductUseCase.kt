package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) {

    operator fun invoke() = run { productDatabaseRepository.getAllProducts() }
}

/*
//Use EmptyBaseUseCase
class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.FlowEmptyBaseUseCase< List<Product>> {

    override suspend fun invoke(): Flow<List<Product>> {
        return productDatabaseRepository.getAllProducts().
    }

}*/

/*
//Use EmptyBaseUseCase
class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.EmptyBaseUseCase< Flow<List<Product>>> {

    override suspend fun invoke(): Flow<List<Product>> {
        return productDatabaseRepository.getAllProducts()
    }

}*/