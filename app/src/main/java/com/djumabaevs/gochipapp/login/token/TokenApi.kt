package com.djumabaevs.gochipapp.login.token

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

const val baseUrl = "https://syncrasy-sso-dev.apps.env02.syncrasy.dev/"

@FormUrlEncoded
@POST("auth/realms/Syncrasy/protocol/openid-connect/token")
suspend fun getJWTToken(
@Field("username")
)