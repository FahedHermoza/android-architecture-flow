package com.fahed.composeapp.domain.usecase.product

import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.core.data.DataState
import com.fahed.networkapp.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UpdateProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<UpdateProductUseCase.UpdateProductUseCaseParams, Product> {

    data class UpdateProductUseCaseParams(val token: String, val product: Product) :
        BaseUseCase.Params()

    override suspend fun invoke(params: UpdateProductUseCaseParams): Flow<DataState<Product>> {
        val (token, product) = params
        return productRepository.update(token, product)
    }
}