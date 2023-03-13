package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product

class DeleteProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<DeleteProductUseCase.DeleteProductUseCaseParams, Unit> {

    override suspend fun invoke(params: DeleteProductUseCaseParams) {
        val (product) = params // destructuring
        productDatabaseRepository.deleteProduct(product)
    }

    data class DeleteProductUseCaseParams(val product: Product) : BaseUseCase.Params()
}