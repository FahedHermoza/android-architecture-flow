package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class DeleteProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<DeleteProductUseCase.DeleteProductUseCaseParams, Unit> {

    override suspend fun invoke(params: DeleteProductUseCaseParams) {
        val (product) = params // destructuring
        productDatabaseRepository.deleteProduct(product)
    }

    data class DeleteProductUseCaseParams(val product: Product) : BaseUseCase.Params()
}