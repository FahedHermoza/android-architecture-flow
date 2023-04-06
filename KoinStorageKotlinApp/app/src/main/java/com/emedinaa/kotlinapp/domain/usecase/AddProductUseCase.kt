package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class AddProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<AddProductUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        val (product) = params // destructuring
        productDatabaseRepository.addProduct(product)
    }

    data class Params(val product: Product) : BaseUseCase.Params()
}