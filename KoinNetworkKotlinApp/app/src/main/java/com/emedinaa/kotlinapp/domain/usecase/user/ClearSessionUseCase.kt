package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class ClearSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke() = productSessionRepository.clearSession()
}