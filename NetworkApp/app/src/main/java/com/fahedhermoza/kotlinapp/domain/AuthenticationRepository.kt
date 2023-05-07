package com.fahedhermoza.kotlinapp.domain

import com.fahedhermoza.kotlinapp.core.data.DataState
import com.fahedhermoza.kotlinapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun login(username: String?, password: String?): Flow<DataState<User>>
}