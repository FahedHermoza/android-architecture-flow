package com.fahedhermoza.kotlinapp.domain.usecase.product

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.model.MultipleDelete
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