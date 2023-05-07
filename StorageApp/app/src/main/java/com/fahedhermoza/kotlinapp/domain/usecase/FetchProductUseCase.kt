package com.fahedhermoza.kotlinapp.domain.usecase

import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.Product
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