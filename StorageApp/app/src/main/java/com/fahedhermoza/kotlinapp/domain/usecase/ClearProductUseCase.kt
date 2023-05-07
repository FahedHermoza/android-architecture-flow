package com.fahedhermoza.kotlinapp.domain.usecase

import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository

class ClearProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.EmptyBaseUseCase<Unit> {

    override suspend fun invoke() {
        return productDatabaseRepository.deleteAllProduct()
    }

}