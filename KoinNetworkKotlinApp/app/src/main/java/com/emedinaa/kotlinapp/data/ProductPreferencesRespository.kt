package com.emedinaa.kotlinapp.data

import com.emedinaa.kotlinapp.data.storage.local.PreferencesHelper
import com.emedinaa.kotlinapp.domain.ProductSessionRepository

class ProductPreferencesRespository(private val preferencesHelper: PreferencesHelper): ProductSessionRepository {
    override fun saveSession(username: String,token:String, objectId:String) {
        preferencesHelper.saveSession(username, token, objectId)
    }

    override fun getSession(): String? = preferencesHelper.session()

    override fun getObjectId(): String? = preferencesHelper.objectId()

    override fun isActiveSession(): Boolean = preferencesHelper.isSignedIn()

    override fun clearSession() = preferencesHelper.clearSession()

}