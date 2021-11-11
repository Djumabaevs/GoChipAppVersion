package com.djumabaevs.gochipapp.vets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityPannelBinding
import com.djumabaevs.gochipapp.databinding.ActivityVetBinding
import com.djumabaevs.gochipapp.pannels.PersonAdapter
import com.djumabaevs.gochipapp.pets.PetsListAdapter
import kotlinx.coroutines.channels.Channel

class VetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personInfo = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
        val adapter = PersonAdapter(personInfo)
        binding.vetRecycler.layoutManager = LinearLayoutManager(this)
        binding.vetRecycler.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        val uidTest = "919a2590-25b2-4b8b-a0c1-abd4c22b2917"

        lifecycleScope.launchWhenResumed {
            var personUid: String? = null
            var profileType: Int = 100
            for (item in channel) {
                val response = try {
                    apolloClient(this@VetActivity)
                        .query(GetPersonsDataQuery(
                            person_uid = Input.fromNullable(personUid),
                            profile_type = Input.fromNullable(profileType)
                        )
                        )
                        .await()
                } catch (e: ApolloException) {
                    Log.d("PersonListGoChip", "Failure", e)
                    return@launchWhenResumed
                }

                Log.d("PersonListGoChip", "response person: $response")

                binding.progressBar.visibility = View.GONE

                val checkVetOrNot = apolloClient(this@VetActivity).query(
                    GetPersonsDataQuery()
                ).toDeferred().await()

                val profiles = checkVetOrNot
                    .data?.ui_pannels_to_users?.firstOrNull()?.profile_type

                val usersProfile = checkVetOrNot.data?.ui_pannels_to_users?.filter { it.profile_type == 200 } ?: emptyList()

                val newPersonData = response.data?.ui_pannels_to_users


                if (usersProfile != null) {
                    personInfo.addAll(usersProfile)
                    adapter.notifyDataSetChanged()
                }


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}