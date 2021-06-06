package com.emedinaa.kotlinapp.data.remote

import com.emedinaa.kotlinapp.core.data.DataResult
import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.core.data.safeApiCall
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.data.storage.Mapper
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthenticationRemoteDataSource : AuthenticationDataSource, KoinComponent {

    private val serviceApi: ProductApiClient by inject()

    override suspend fun login(username: String?, password: String?): UserDTO {
        val raw = LogInRaw(username, password)
        val logInResponse =
            serviceApi.logInBL(ProductConstant.APPLICATION_ID, ProductConstant.REST_API_KEY, raw)
        return UserDTO(
            logInResponse.token,
            logInResponse.email,
            logInResponse.objectId,
            logInResponse.code,
            logInResponse.message
        )
    }

}