package com.fahed.composeapp.domain.model

import java.io.Serializable

data class Product(var id:Int, var name:String, var cost: Double, var description:String, var logo: Int):
    Serializable{
    constructor(): this (id = 0, name = "", cost = 0.0, description = "",0)
    }