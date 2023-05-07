package com.fahedhermoza.kotlinapp.domain.usecase.user

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.core.domain.usecase.BaseUseCase
import com.fahedhermoza.kotlinapp.domain.AuthenticationRepository
import com.fahedhermoza.kotlinapp.domain.model.User
import com.fahedhermoza.kotlinapp.domain.usecase.user.AuthenticateUserUseCase.AuthenticateUserUseCaseParams
import kotlinx.coroutines.flow.Flow

class AuthenticateUserUseCase(private val authenticationRepository: AuthenticationRepository) :
    BaseUseCase.FlowDataBaseUseCase<AuthenticateUserUseCaseParams, User> {

    override suspend fun invoke(params: AuthenticateUserUseCaseParams): Flow<DataState<User>> {
        val (username, password) = params // destructuring
        return authenticationRepository.login(username, password)
    }

    data class AuthenticateUserUseCaseParams(val username: String?, val password: String?) :
        BaseUseCase.Params()
}