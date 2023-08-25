package com.fahed.composeapp.domain.usecase.user

import com.fahed.composeapp.core.data.DataState
import com.fahed.composeapp.domain.AuthenticationRepository
import com.fahed.composeapp.domain.model.User
import com.fahed.composeapp.domain.usecase.user.AuthenticateUserUseCase.AuthenticateUserUseCaseParams
import com.fahed.networkapp.core.domain.BaseUseCase
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