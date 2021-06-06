package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository

class ClearProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.EmptyBaseUseCase<Unit> {

    override suspend fun invoke() {
        return productDatabaseRepository.deleteAllProduct()
    }

}