package com.emedinaa.kotlinapp.data.remote

import com.emedinaa.kotlinapp.data.StorageResult
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthenticationRemoteDataSource: AuthenticationDataSource, KoinComponent  {

    private val serviceApi: ProductApiClient by inject()

    override suspend fun login(username: String?, password: String?): StorageResult<UserDTO> {
        return try {
            val raw = LogInRaw(username,password)
            val response = serviceApi?.logInBL(ProductConstant.APPLICATION_ID, ProductConstant.REST_API_KEY,raw)

            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(UserDTO(responseBody?.token,responseBody?.email,responseBody?.objectId))
                } else {
                    StorageResult.Failure(Exception(it.errorBody()?.string()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

}