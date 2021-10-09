package com.djumabaevs.gochipapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await

class MainActivity : AppCompatActivity() {

    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://gochip-external-new.wcvie.at/v1/graphql")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        try {
//            val response = apolloClient.query(GetPetQuery()).await()
//            // handle response and return it
//        } catch (e: Exception) {
//            // handle error and return response
//        }


    }
}