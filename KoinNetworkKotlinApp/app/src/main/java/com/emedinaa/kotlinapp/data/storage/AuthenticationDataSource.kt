package com.emedinaa.kotlinapp.data.remote

interface AuthenticationDataSource {

    suspend fun login(username:String?,password:String?): UserDTO
}