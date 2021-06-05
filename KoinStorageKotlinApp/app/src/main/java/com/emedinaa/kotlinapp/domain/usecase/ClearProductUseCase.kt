package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.domain.ProductRepository

class ClearProductUseCase(private val productDatabaseRepository: ProductRepository) {

    suspend operator fun invoke() = run { productDatabaseRepository.deleteAllProduct()}
}