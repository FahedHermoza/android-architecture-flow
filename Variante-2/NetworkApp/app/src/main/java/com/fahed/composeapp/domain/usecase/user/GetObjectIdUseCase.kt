package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.domain.ProductSessionRepository

class GetObjectIdUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(): String? = productSessionRepository.getObjectId()
}