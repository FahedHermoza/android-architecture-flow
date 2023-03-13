package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product

class AddProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<AddProductUseCase.AddProductUseCaseParams, Unit> {

    override suspend fun invoke(params: AddProductUseCaseParams) {
        val (product) = params // destructuring
        productDatabaseRepository.addProduct(product)
    }

    data class AddProductUseCaseParams(val product: Product) : BaseUseCase.Params()
}