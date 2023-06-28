package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.domain.ProductSessionRepository
import com.fahed.networkapp.core.domain.BaseUseCase

class SaveSessionUseCase (private val productSessionRepository: ProductSessionRepository) :
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