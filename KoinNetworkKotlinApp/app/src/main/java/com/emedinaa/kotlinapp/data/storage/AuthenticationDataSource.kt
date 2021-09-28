package com.emedinaa.kotlinapp.data.storage

import com.emedinaa.kotlinapp.data.storage.remote.UserDTO

interface AuthenticationDataSource {

    suspend fun login(username: String?, password: String?): UserDTO
}