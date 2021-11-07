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

        val personInfo = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
        val adapter = PersonAdapter(personInfo)
        binding.personRecycler.layoutManager = LinearLayoutManager(this)
        binding.personRecycler.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        val uidTest = "919a2590-25b2-4b8b-a0c1-abd4c22b2917"

        lifecycleScope.launchWhenResumed {
            var personUid: String? = null
            var profileType: Int = 200
            for (item in channel) {
                val response = try {
                    apolloClient(this@PannelActivity)
                        .query(GetPersonsDataQuery(
                            person_uid = Input.fromNullable(personUid),
                            profile_type = Input.fromNullable(profileType)))
                        .await()
                } catch (e: ApolloException) {
                    Log.d("PersonListGoChip", "Failure", e)
                    return@launchWhenResumed
                }

                binding.progressBar.visibility = View.GONE

                val newPersonData = response.data?.ui_pannels_to_users



                if (newPersonData != null) {
                    personInfo.addAll(newPersonData)
                    adapter.notifyDataSetChanged()
                }
                //    petUid = response.data?.pets?.get(0)?.pet_uid.toString()


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}