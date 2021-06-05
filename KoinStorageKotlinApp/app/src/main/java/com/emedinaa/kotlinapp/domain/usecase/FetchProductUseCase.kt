package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.domain.ProductRepository

class FetchProductUseCase(private val productDatabaseRepository: ProductRepository) {

    operator fun invoke() = run { productDatabaseRepository.getAllProducts() }
}