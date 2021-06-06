package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class GetObjectIdUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke():String? = productSessionRepository.getObjectId()
}