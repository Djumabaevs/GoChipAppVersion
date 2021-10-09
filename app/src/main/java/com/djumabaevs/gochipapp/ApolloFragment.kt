package com.djumabaevs.gochipapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.djumabaevs.gochipapp.databinding.FragmentApolloBinding

//import com.djumabaevs.gochipapp.databinding.ApolloFragmentBinding

 val apolloClient = ApolloClient.builder()
    .serverUrl("https://gochip-external-new.wcvie.at/v1/graphql")
    .build()

//        try {
//            val response = apolloClient.query(GetPetQuery()).await()
//            // handle response and return it
//        } catch (e: Exception) {
//            // handle error and return response
//        }

class ApolloFragment : Fragment() {

    val apolloClient = ApolloClient.builder()
        .serverUrl("https://gochip-external-new.wcvie.at/v1/graphql")
        .build()

    private lateinit var binding: FragmentApolloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApolloBinding.inflate(inflater)

        return binding.root
    }


    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {


//            Log.d("PetInfo", "Success ${response.data}")

            val pet = response.data?.pets
            binding.petName.text = pe


        }
    }
}