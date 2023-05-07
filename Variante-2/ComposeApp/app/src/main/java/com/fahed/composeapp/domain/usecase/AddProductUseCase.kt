package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product

class AddProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<AddProductUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        val (product) = params // destructuring
        productDatabaseRepository.addProduct(product)
    }

    data class Params(val product: Product) : BaseUseCase.Params()
}