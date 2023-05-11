package com.fahed.composeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val token: String,
    val email: String,
    val objectId: String,
    val code: Int? = 0,
    val message: String? = ""
) : Parcelable