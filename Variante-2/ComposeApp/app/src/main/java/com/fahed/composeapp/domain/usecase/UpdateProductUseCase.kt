package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product

class UpdateProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<UpdateProductUseCase.Params, Unit> {

    data class Params(val product: Product) : BaseUseCase.Params()

    override suspend fun invoke(params: Params) {
        val (product) = params // destructuring
        productDatabaseRepository.updateProduct(product)
    }
}