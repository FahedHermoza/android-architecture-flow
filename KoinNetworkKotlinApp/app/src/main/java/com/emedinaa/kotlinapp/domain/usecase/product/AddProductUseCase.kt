package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class AddProductUseCase(private val productRepository: ProductRepository):
    BaseUseCase.FlowDataBaseUseCase<AddProductUseCase.AddProductUseCaseParams, Product>{

    data class AddProductUseCaseParams(val token:String, val product: Product) : BaseUseCase.Params()

    override suspend fun invoke(params: AddProductUseCaseParams): Flow<DataState<Product>> {
        val (token, product) = params
        return productRepository.add(token, product)
    }
}