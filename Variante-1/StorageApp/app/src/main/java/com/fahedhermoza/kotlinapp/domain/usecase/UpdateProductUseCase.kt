package com.fahedhermoza.kotlinapp.domain.usecase

import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.Product

class UpdateProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<UpdateProductUseCase.Params, Unit> {

    data class Params(val product: Product) : BaseUseCase.Params()

    override suspend fun invoke(params: Params) {
        val (product) = params // destructuring
        productDatabaseRepository.updateProduct(product)
    }
}