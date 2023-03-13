package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository

class ClearProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.EmptyBaseUseCase<Unit> {

    override suspend fun invoke() {
        return productDatabaseRepository.deleteAllProduct()
    }

}