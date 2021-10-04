package com.emedinaa.kotlinapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var objectId: String?,
    var name: String?,
    var description: String?,
    var cost: Double?,
    var logo: String?,
    var code: String?
) : Parcelable