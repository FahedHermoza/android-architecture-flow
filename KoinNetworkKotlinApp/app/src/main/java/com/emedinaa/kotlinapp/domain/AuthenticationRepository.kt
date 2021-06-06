package com.emedinaa.kotlinapp.domain

import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.User

interface AuthenticationRepository {
    suspend fun login(username: String?, password: String?): StorageResult<User>
}