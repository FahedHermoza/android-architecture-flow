package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class AddProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<AddProductUseCase.AddProductUseCaseParams, Unit> {

    override suspend fun invoke(params: AddProductUseCaseParams) {
        val (product) = params // destructuring
        productDatabaseRepository.addProduct(product)
    }

    data class AddProductUseCaseParams(val product: Product) : BaseUseCase.Params()
}