package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.domain.ProductSessionRepository

class VerifySessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(): Boolean = productSessionRepository.isActiveSession()
}