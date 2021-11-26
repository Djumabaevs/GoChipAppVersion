package com.djumabaevs.gochipapp.login.token

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Long? = 0,
    @SerializedName("refresh_expires_in")
    val refreshExpiresIn: Long? = 0,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("not-before-policy")
    val notBeforeType: Int? = 0,
    @SerializedName("session_state")
    val sessionState: String,
    @SerializedName("scope")
    val scope: String
) : BaseResponse()