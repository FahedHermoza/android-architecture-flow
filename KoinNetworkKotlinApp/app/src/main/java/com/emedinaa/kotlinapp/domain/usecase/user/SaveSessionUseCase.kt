package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.core.domain.usecase.BaseUseCase
import com.emedinaa.kotlinapp.domain.ProductSessionRepository

class SaveSessionUseCase(private val productSessionRepository: ProductSessionRepository) :
    BaseUseCase.SimpleBaseUseCase<SaveSessionUseCase.SaveSessionUseCaseParams, Unit> {

    data class SaveSessionUseCaseParams(
        val username: String,
        val token: String,
        val objectId: String
    ) : BaseUseCase.Params()

    override suspend fun invoke(params: SaveSessionUseCaseParams) {
        val (username, token, objectId) = params // destructuring
        productSessionRepository.saveSession(username, token, objectId)
    }
}