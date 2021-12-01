package com.djumabaevs.gochipapp.login.newLogin

import com.djumabaevs.gochipapp.login.newLogin.Util.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            //  .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(NetworkResponseAdapterFactory())
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val api: TokenApi by lazy {
        retrofit.create(TokenApi::class.java)
    }
}