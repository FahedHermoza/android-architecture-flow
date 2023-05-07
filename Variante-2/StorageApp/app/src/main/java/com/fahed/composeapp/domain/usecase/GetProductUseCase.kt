package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.core.domain.BaseUseCase
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product

class GetProductUseCase(private val productDatabaseRepository: ProductRepository) :
    BaseUseCase.SimpleBaseUseCase<GetProductUseCase.Params, Product> {

    override suspend fun invoke(params: Params): Product {
        val (idProduct) = params // destructuring
        return productDatabaseRepository.getProduct(idProduct)
    }

    data class Params(val idProduct: Int) : BaseUseCase.Params()
}