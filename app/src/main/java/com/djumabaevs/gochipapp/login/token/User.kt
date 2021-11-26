package com.djumabaevs.gochipapp.login.token

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firstname")
    var firstName: String?,
    @SerializedName("lastname")
    val lastName: String? = null,
    @SerializedName("access_token")
    val token: String,

    var phone: String?,
    val email: String?,
    var password: String?
) : BaseResponse()