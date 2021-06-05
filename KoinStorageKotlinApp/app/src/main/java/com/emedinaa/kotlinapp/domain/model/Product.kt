package com.emedinaa.kotlinapp.domain.model

import java.io.Serializable

data class Product(var id:Int, var name:String, var cost: Double, var description:String, var logo: Int):
    Serializable