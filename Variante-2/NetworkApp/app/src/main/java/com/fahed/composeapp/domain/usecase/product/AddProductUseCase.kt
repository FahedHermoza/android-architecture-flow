package com.fahed.composeapp.domain.usecase.product

import com.fahed.composeapp.core.data.DataState
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product
import com.fahed.networkapp.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class AddProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<AddProductUseCase.AddProductUseCaseParams, Product> {

    data class AddProductUseCaseParams(val token: String, val product: Product) :
        BaseUseCase.Params()

    override suspend fun invoke(params: AddProductUseCaseParams): Flow<DataState<Product>> {
        val (token, product) = params
        return productRepository.add(token, product)
    }
}