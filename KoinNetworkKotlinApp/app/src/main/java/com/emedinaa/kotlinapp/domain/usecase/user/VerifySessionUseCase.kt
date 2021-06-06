package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class VerifySessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke():Boolean = productSessionRepository.isActiveSession()
}