package com.fahed.networkapp.core.base

import com.google.gson.Gson

data class ErrorHttpException(val code: Int, val message: String, val errorData: Any?) {
    companion object {
        fun fromJsonString(jsonString: String): ErrorHttpException {
            return Gson().fromJson(jsonString, ErrorHttpException::class.java)
        }
    }
}
