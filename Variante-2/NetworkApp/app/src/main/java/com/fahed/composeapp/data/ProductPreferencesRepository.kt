package com.fahed.composeapp.data

import com.fahed.composeapp.data.storage.local.PreferencesHelper
import com.fahed.composeapp.domain.ProductSessionRepository

class ProductPreferencesRepository(private val preferencesHelper: PreferencesHelper) :
    ProductSessionRepository {
    override fun saveSession(username: String, token: String, objectId: String) {
        preferencesHelper.saveSession(username, token, objectId)
    }

    override fun getSession(): String? = preferencesHelper.session()

    override fun getObjectId(): String? = preferencesHelper.objectId()

    override fun isActiveSession(): Boolean = preferencesHelper.isSignedIn()

    override fun clearSession() = preferencesHelper.clearSession()

}