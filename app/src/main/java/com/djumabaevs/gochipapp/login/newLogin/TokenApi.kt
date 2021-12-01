package com.djumabaevs.gochipapp.login.newLogin

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

const val baseUrl = "https://food-st1.maddevs.co/api/v2/"

interface TokenApi {
    @POST("auth/realms/syncrasy/protocol/openid-connect/token/")
    @FormUrlEncoded
    suspend fun getJWTToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String
    ): Response<TokenResponse>

    @GET("order/get_surcharge")
    suspend fun fetchSurcharge(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<SurchargeResponse>

    @GET("category")
    suspend fun getData() : Response<List<DataResponse>>

}

data class DataResponse(
    @SerializedName("name")
    val name: String
)

data class SurchargeResponse(
    val isSuccess: Boolean,
    val cost: Float? = 0F,
    @SerializedName("zone_name")
    val zoneName: String
)

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