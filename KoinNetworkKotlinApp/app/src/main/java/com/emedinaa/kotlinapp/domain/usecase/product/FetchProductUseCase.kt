package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class FetchProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<FetchProductUseCase.FetchProductUseCaseParams, List<Product>> {

    data class FetchProductUseCaseParams(val token: String) : BaseUseCase.Params()

    override suspend fun invoke(params: FetchProductUseCaseParams): Flow<DataState<List<Product>>> {
        return productRepository.fetch(params.token)
    }
}