package com.fahedhermoza.kotlinapp.data

import com.fahedhermoza.kotlinapp.core.data.DataResponseHandler
import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.core.data.safeApiCall
import com.fahedhermoza.kotlinapp.data.storage.AuthenticationDataSource
import com.fahedhermoza.kotlinapp.data.storage.Mapper
import com.fahedhermoza.kotlinapp.data.storage.remote.UserDTO
import com.fahedhermoza.kotlinapp.domain.AuthenticationRepository
import com.fahedhermoza.kotlinapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRemoteRepository(private val dataSource: AuthenticationDataSource) :
    AuthenticationRepository {

    override suspend fun login(username: String?, password: String?): Flow<DataState<User>> = flow {
        // 1. emit a 'loading event'
        emit(DataState.loading(true))

        // 2. apiCall / cacheCall
        val apiCall = safeApiCall { dataSource.login(username, password) }

        // 3. process '..call' and emit it
        emit(
            object : DataResponseHandler<User, UserDTO>(response = apiCall) {
                override suspend fun handleSuccess(resultObj: UserDTO): DataState<User> {
                    return if (resultObj.token != "") {
                        val user = Mapper.userDTOToUser(resultObj)
                        DataState.data(message = "message", data = user)
                    } else {
                        DataState.error(code = 500, message = "message")
                    }
                }
            }.getResult()
        )
    }
}