package com.emedinaa.kotlinapp.data.remote

import com.google.gson.annotations.SerializedName


/**
 * @author Eduardo Medina
 */

//User
data class UserDTO(
    @SerializedName("user-token") val token:String?,
    val email:String?,
    val objectId:String?
)


data class LogInRaw(val login:String?,
                    val password:String?
)

data class LogInResponse(@SerializedName("user-token") val token:String?,
                         val email:String?,
                         val objectId:String?
)

//Product
data class ProductRaw(
    val name:String?,
    val description:String?,
    val cost:Double?,
    val logo:String?,
    val code:String?
)

//@SerializedName("name")
data class ProductDTO(
    val objectId:String?,
    val name:String?,
    val description:String?,
    val cost:Double?,
    val logo:String?,
    val code:String?
)

open class BaseResponse(private val status: Int?, val msg: String?) {
    companion object {
        const val STATUS_CODE: Int = 200
    }

    protected fun isSuccess(): Boolean {
        return status == STATUS_CODE
    }
}

class ProductResponse(
        @field:SerializedName("objectId") val objectId:String?,
        @field:SerializedName("name") val name:String?,
        @field:SerializedName("description") val description:String?,
        @field:SerializedName("cost") val cost:Double?,
        @field:SerializedName("logo") val logo:String?,
        @field:SerializedName("code") val code:String?
)

class DeleteResponse(
        val deletionTime: Long?
)

data class DeleteDTO(
        val deletionTime: Long?
)