package com.fahedhermoza.kotlinapp.data.storage

import com.fahedhermoza.kotlinapp.data.storage.remote.UserDTO

interface AuthenticationDataSource {

    suspend fun login(username: String?, password: String?): UserDTO
}