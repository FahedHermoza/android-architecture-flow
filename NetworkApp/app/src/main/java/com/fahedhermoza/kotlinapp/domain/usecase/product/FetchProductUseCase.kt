package com.fahedhermoza.kotlinapp.domain.usecase.product

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class FetchProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<FetchProductUseCase.FetchProductUseCaseParams, List<Product>> {

    data class FetchProductUseCaseParams(val token: String) : BaseUseCase.Params()

    override suspend fun invoke(params: FetchProductUseCaseParams): Flow<DataState<List<Product>>> {
        return productRepository.fetch(params.token)
    }
}