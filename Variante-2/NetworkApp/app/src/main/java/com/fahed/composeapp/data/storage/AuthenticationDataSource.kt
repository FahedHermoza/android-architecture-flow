package com.fahed.composeapp.data.storage

import com.fahed.composeapp.data.storage.remote.UserDTO

interface AuthenticationDataSource {

    suspend fun login(username: String?, password: String?): UserDTO
}