package com.emedinaa.kotlinapp.domain.model

import java.io.Serializable

data class Product( var objectId:String?,
                    var name:String?,
                    var description:String?,
                    var cost:Double?,
                    var logo:String?,
                    var code:String?): Serializable