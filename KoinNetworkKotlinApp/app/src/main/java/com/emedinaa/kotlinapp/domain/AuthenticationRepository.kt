package com.emedinaa.kotlinapp.domain

import com.emedinaa.kotlinapp.core.data.DataState
import com.emedinaa.kotlinapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun login(username: String?, password: String?): Flow<DataState<User>>
}