package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.domain.ProductSessionRepository

class GetSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(): String? = productSessionRepository.getSession()
}