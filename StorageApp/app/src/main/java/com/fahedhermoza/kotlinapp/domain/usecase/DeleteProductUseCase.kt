package com.fahedhermoza.kotlinapp.domain.usecase

import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.Product

class DeleteProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<DeleteProductUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        val (product) = params // destructuring
        productDatabaseRepository.deleteProduct(product)
    }

    data class Params(val product: Product) : BaseUseCase.Params()
}