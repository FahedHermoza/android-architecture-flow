package com.fahedhermoza.kotlinapp.core.domain.usecase

import com.fahedhermoza.kotlinapp.core.data.DataState
import kotlinx.coroutines.flow.Flow

interface BaseUseCase {

    interface FlowDataBaseUseCase<params : Params, T : Any?> : BaseUseCase {
        suspend operator fun invoke(params: params): Flow<DataState<T>>
    }

    interface FlowDataEmptyBaseUseCase<T : Any?> : BaseUseCase {
        suspend operator fun invoke(): Flow<DataState<T>>
    }

    interface FlowBaseUseCase<params : Params, T : Any?> : BaseUseCase {
        suspend operator fun invoke(params: params): Flow<T>
    }

    interface FlowEmptyBaseUseCase<T : Any?> : BaseUseCase {
        suspend operator fun invoke(): Flow<T>
    }

    interface SimpleBaseUseCase<params : Params, T : Any?> : BaseUseCase {
        suspend operator fun invoke(params: params): T
    }

    interface EmptyBaseUseCase<T : Any?> : BaseUseCase {
        suspend operator fun invoke(): T
    }

    abstract class Params

}