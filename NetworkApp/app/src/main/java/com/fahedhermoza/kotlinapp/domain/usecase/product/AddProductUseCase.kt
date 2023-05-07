package com.fahedhermoza.kotlinapp.domain.usecase.product

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.Product
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