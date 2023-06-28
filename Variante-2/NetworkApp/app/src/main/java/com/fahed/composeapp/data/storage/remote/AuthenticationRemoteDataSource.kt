package com.fahed.composeapp.data.storage.remote

import com.fahed.composeapp.data.storage.AuthenticationDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthenticationRemoteDataSource : AuthenticationDataSource, KoinComponent {

    private val serviceApi: ProductApiClient by inject()

    override suspend fun login(username: String?, password: String?): UserDTO {
        val request = LogInRequest(username, password)
        val logInResponse =
            serviceApi.login(ProductConstant.APPLICATION_ID, ProductConstant.REST_API_KEY, request)
        return UserDTO(
            logInResponse.token,
            logInResponse.email,
            logInResponse.objectId,
            logInResponse.code,
            logInResponse.message
        )
    }

}