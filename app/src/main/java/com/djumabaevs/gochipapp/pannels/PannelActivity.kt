package com.djumabaevs.gochipapp.pannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityPannelBinding
import com.djumabaevs.gochipapp.pets.PetsListAdapter
import kotlinx.coroutines.channels.Channel

class PannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personInfo = mutableListOf<GetPersonsDataQuery.Person>()
        val adapter = PersonAdapter(personInfo)
        binding.personRecycler.layoutManager = LinearLayoutManager(this)
        binding.personRecycler.adapter = adapter

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }



        lifecycleScope.launchWhenResumed {
            var personUid: String? = null
            for (item in channel) {
                val response = try {
                    apolloClient(this@PannelActivity).query(GetPersonsDataQuery(person_uid = Input.fromNullable(personUid))).await()
                } catch (e: ApolloException) {
                    Log.d("PetsListGoChip", "Failure", e)
                    return@launchWhenResumed
                }


                val newPersonData = response.data?.ui_pannels_to_users?.firstOrNull()?.person



                if (newPersonData != null) {
                    personInfo.addAll(listOf(newPersonData))
                    adapter.notifyDataSetChanged()
                }
                //    petUid = response.data?.pets?.get(0)?.pet_uid.toString()


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}