package com.djumabaevs.gochipapp.login.token

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

const val baseUrl = "https://syncrasy-sso-dev.apps.env02.syncrasy.dev/"

interface TokenApi {
    @FormUrlEncoded
    @POST("auth/realms/Syncrasy/protocol/openid-connect/token")
    suspend fun getJWTToken(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String
    ): TokenResponse
}

val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


