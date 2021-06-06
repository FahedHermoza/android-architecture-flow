package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class UpdateProductUseCase(private val productRepository: ProductRepository):
    BaseUseCase.FlowDataBaseUseCase<UpdateProductUseCase.UpdateProductUseCaseParams, Product>{

    data class UpdateProductUseCaseParams(val token:String, val product: Product) : BaseUseCase.Params()

    override suspend fun invoke(params: UpdateProductUseCaseParams): Flow<DataState<Product>> {
        val (token, product) = params
        return productRepository.update(token, product)
    }
}