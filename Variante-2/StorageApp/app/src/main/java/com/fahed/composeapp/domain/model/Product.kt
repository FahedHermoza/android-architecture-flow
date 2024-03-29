package com.fahed.composeapp.domain.model

import java.io.Serializable

data class Product(var id:Int, var name:String, var cost: Double, var description:String, var logo: Int):
    Serializable{
    constructor(): this (id = 0, name = "", cost = 0.0, description = "",logo = 0)
    fun isEmpty():Boolean = (this.id == 0 && this.name == "" && this.cost.equals(0.0) && this.logo == 0)
    }
