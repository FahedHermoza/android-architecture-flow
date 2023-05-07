package com.fahedhermoza.kotlinapp.domain.usecase.user

import com.fahedhermoza.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class ClearSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke() = productSessionRepository.clearSession()
}