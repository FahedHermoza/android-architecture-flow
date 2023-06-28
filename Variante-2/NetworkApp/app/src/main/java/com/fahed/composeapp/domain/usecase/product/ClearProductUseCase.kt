package com.fahed.composeapp.domain.usecase.product

import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.model.MultipleDelete
import com.fahed.composeapp.core.data.DataState
import com.fahed.networkapp.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class ClearProductUseCase(private val productRepository: ProductRepository) :
    BaseUseCase.FlowDataBaseUseCase<ClearProductUseCase.ClearProductUseCaseParams, MultipleDelete> {

    data class ClearProductUseCaseParams(val token: String, val minimalCost: Double) :
        BaseUseCase.Params()

    override suspend fun invoke(params: ClearProductUseCaseParams): Flow<DataState<MultipleDelete>> {
        val (token, minimalCost) = params
        return productRepository.clear(token, minimalCost)
    }
}