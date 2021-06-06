package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.domain.ProductSessionRepository

/**
 * @author Eduardo Medina
 */
class SaveSessionUseCase(private val productSessionRepository: ProductSessionRepository) {

    operator fun invoke(username: String,token:String, objectId:String) {
        productSessionRepository.saveSession(username, token, objectId)
    }
}