package com.fahed.composeapp.data

import com.fahed.composeapp.core.data.DataResponseHandler
import com.fahed.composeapp.core.data.DataState
import com.fahed.composeapp.core.data.safeApiCall
import com.fahed.composeapp.data.storage.AuthenticationDataSource
import com.fahed.composeapp.data.storage.Mapper
import com.fahed.composeapp.data.storage.remote.UserDTO
import com.fahed.composeapp.domain.AuthenticationRepository
import com.fahed.composeapp.domain.model.User
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