package com.fahedhermoza.kotlinapp.domain.usecase.user

import com.fahedhermoza.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class VerifySessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(): Boolean = productSessionRepository.isActiveSession()
}