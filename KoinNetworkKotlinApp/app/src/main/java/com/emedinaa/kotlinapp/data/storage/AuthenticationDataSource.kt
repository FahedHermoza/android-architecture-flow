package com.emedinaa.kotlinapp.data.remote

import com.emedinaa.kotlinapp.data.StorageResult

interface AuthenticationDataSource {

    suspend fun login(username:String?,password:String?): StorageResult<UserDTO>
}