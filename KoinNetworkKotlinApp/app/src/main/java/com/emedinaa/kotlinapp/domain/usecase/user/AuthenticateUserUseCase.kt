package com.emedinaa.kotlinapp.domain.usecase.user

import com.emedinaa.kotlinapp.domain.AuthenticationRepository

class AuthenticateUserUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend operator fun invoke(username: String?, password: String?) = run{
        authenticationRepository.login(username, password)
    }
}