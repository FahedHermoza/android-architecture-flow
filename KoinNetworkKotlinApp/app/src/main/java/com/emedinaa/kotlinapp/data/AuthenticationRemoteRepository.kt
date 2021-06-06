package com.emedinaa.kotlinapp.data.storage

import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.data.remote.AuthenticationDataSource
import com.emedinaa.kotlinapp.data.remote.UserDTO
import com.emedinaa.kotlinapp.domain.AuthenticationRepository
import com.emedinaa.kotlinapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRemoteRepository(private val dataSource: AuthenticationDataSource): AuthenticationRepository {

    override suspend fun login(username: String?, password: String?): StorageResult<User> = withContext(
        Dispatchers.IO){
        when (val result = dataSource.login(username,password)) {
            is StorageResult.Complete -> StorageResult.Complete(Mapper.userDTOToUser(result.data?:
            UserDTO("","","")))
            is StorageResult.Failure -> StorageResult.Failure(result.exception)
            else -> StorageResult.UnAuthorized(Exception())
        }
    }
}