package com.djumabaevs.gochipapp.apollo

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(context))
        .build()

    instance = ApolloClient.builder()
 //       .serverUrl("https://gochip-external-new.wcvie.at/v1/graphql")
 //       .serverUrl("https://syn-otp-server-external.production.syncrasy.dev/graphql")
        .serverUrl("https://syn-vet-app-api-external.production.syncrasy.dev/v1/graphql")
      //  .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory("wss://gochip-external-new.wcvie.at/v1/graphql", okHttpClient))
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}

private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
         //   .addHeader("Authorization", User.getToken(context) ?: "")
            .build()

        return chain.proceed(request)
    }
}