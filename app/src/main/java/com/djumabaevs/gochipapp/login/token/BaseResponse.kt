package com.djumabaevs.gochipapp.login.token

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    var success: Boolean = false

    val error: String? = null

    @SerializedName("error_type")
    val errorType: String? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("error_description")
    val errorDescription: String? = null

    val errorMessage: String
        get() {
            return errorDescription ?: message ?: ""
        }
}