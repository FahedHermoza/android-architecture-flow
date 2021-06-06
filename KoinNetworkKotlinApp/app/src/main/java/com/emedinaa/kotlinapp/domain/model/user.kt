package com.emedinaa.kotlinapp.domain.model

import android.os.Message
import android.os.Parcelable
import java.io.Serializable

data class User(
    val token: String,
    val email: String,
    val objectId: String,
    val code: Int? = 0,
    val message: String? = ""
) : Serializable