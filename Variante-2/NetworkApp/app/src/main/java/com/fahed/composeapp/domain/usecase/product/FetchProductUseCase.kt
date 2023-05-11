package com.fahed.composeapp.domain.usecase.product

import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.core.data.DataState
import com.fahed.networkapp.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class FetchProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<FetchProductUseCase.FetchProductUseCaseParams, List<Product>> {

    data class FetchProductUseCaseParams(val token: String) : BaseUseCase.Params()

    override suspend fun invoke(params: FetchProductUseCaseParams): Flow<DataState<List<Product>>> {
        return productRepository.fetch(params.token)
    }
}