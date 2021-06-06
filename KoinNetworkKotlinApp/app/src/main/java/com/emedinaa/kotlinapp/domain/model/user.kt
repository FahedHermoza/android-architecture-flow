package com.emedinaa.kotlinapp.domain.model

import android.os.Parcelable
import java.io.Serializable

data class User(val token:String, val email:String, val objectId:String): Serializable