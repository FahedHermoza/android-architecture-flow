package com.fahed.composeapp.domain

import com.fahed.composeapp.core.data.DataState
import com.fahed.composeapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun login(username: String?, password: String?): Flow<DataState<User>>
}