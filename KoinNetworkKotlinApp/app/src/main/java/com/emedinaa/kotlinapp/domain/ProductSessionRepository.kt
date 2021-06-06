package com.emedinaa.kotlinapp.domain

/**
 * @author Eduardo Medina
 */
interface ProductSessionRepository {

    fun saveSession(username: String,token:String, objectId:String)

    fun getSession(): String?

    fun getObjectId(): String?

    fun isActiveSession():Boolean

    fun clearSession()
}