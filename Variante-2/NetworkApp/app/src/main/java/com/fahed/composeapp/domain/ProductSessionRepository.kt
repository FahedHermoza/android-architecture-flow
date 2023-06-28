package com.fahed.composeapp.domain

interface ProductSessionRepository {

    fun saveSession(username: String, token: String, objectId: String)

    fun getSession(): String?

    fun getObjectId(): String?

    fun isActiveSession(): Boolean

    fun clearSession()
}