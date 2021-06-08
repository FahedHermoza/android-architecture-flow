package com.emedinaa.kotlinapp.domain.model

import com.google.gson.Gson

data class ErrorBody(val code: Int, val message: String, val errorData: Any?){
    companion object{
        fun fromJsonString(jsonString: String): ErrorBody{
            return Gson().fromJson(jsonString, ErrorBody::class.java)
        }
    }

}



