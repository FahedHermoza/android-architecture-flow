package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

//Use FlowEmptyBaseUseCase
class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.FlowEmptyBaseUseCase< List<Product>> {

    override suspend fun invoke(): Flow<List<Product>> {
        return productDatabaseRepository.getAllProducts()
    }

}

/*
//Use EmptyBaseUseCase
class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.EmptyBaseUseCase< Flow<List<Product>>> {

    override suspend fun invoke(): Flow<List<Product>> {
        return productDatabaseRepository.getAllProducts()
    }

}*/