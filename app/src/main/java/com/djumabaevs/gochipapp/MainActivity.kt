package com.djumabaevs.gochipapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pets_list)




    }
}