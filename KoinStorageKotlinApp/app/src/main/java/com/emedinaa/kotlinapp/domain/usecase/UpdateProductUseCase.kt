package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class UpdateProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<UpdateProductUseCase.UpdateProductUseCaseParams, Unit> {

    data class UpdateProductUseCaseParams(val product: Product) : BaseUseCase.Params()

    override suspend fun invoke(params: UpdateProductUseCaseParams) {
        val (product) = params // destructuring
        productDatabaseRepository.updateProduct(product)
    }
}