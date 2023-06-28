package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.domain.ProductSessionRepository

class ClearSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke() = productSessionRepository.clearSession()
}