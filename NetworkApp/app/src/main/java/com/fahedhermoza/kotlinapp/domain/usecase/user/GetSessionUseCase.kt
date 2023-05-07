package com.fahedhermoza.kotlinapp.domain.usecase.user

import com.fahedhermoza.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class GetSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(): String? = productSessionRepository.getSession()
}