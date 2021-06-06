package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class ClearProductUseCase(private val productRepository: ProductRepository):
    BaseUseCase.FlowDataBaseUseCase<ClearProductUseCase.ClearProductUseCaseParams, MultipleDelete>{

    data class ClearProductUseCaseParams(val token: String, val minimalCost: Double) : BaseUseCase.Params()

    override suspend fun invoke(params: ClearProductUseCaseParams): Flow<DataState<MultipleDelete>> {
        val (token, minimalCost) = params
        return productRepository.clear(token, minimalCost)
    }
}