package com.djumabaevs.gochipapp.vets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.djumabaevs.gochipapp.pannels.PannelActivity
import com.djumabaevs.gochipapp.pannels.PersonAdapter
import com.djumabaevs.gochipapp.pannels.VetAdapter
import com.djumabaevs.gochipapp.pets.PetsListAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel

class VetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val personInfo = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
        val adapter = VetAdapter(listOf())
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

                val phone = firebaseAuth.currentUser?.phoneNumber?.replaceFirst("+","")
                val emailSignedIn = firebaseAuth.currentUser?.email

                val mainQuery = apolloClient(this@VetActivity).query(
                    GetPersonsDataQuery()
                ).toDeferred().await()

                val pannels = mainQuery.data?.ui_pannels_to_users?.filter {
                   ( it.person.person_phone == phone &&
                            it.profile_type == 100) || (it.person.person_email == emailSignedIn && it.profile_type == 100)
                }?.sortedBy { it.pannel_order  } ?: emptyList()
//                val phones = checkVetOrNot
//                    .data?.ui_pannels_to_users?.mapNotNull {
//                        it.person.person_phone
//                    } ?: emptyList()
//
//
//                val veterinars = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
//                val people = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
//
//                val personPhone = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
//
//                val containsVet = phones.contains(phone)
//
//                checkVetOrNot.data?.ui_pannels_to_users?.forEach { item ->
//                    if (item.profile_type == 100 ) {
//                        veterinars.add(item)
//                    } else if (item.profile_type == 200 ) {
//                        people.add(item)
//                    }
//                }
//
//                checkVetOrNot.data?.ui_pannels_to_users?.forEach { item ->
//                    if(item.person.person_phone == phone) {
//                        personPhone.add(item)
//                    }
//                }
//
//                val profiles = checkVetOrNot
//                    .data?.ui_pannels_to_users?.firstOrNull()?.profile_type
//
//                val usersProfile = checkVetOrNot.data?.ui_pannels_to_users?.filter { it.profile_type == 200 } ?: emptyList()
//
//                val newPersonData = response.data?.ui_pannels_to_users
//
//                val users = checkVetOrNot.data?.ui_pannels_to_users?.filter {
//                    it.person.person_phone == phone
//                } ?: emptyList()
//
//                val currentUser = mainQuery.data?.ui_pannels_to_users?.firstOrNull {
//                    it.person.person_phone == phone
//                }
//
//                val pannel = currentUser?.pannel

//                pannel.pannel_name


//
//                val v100 = users.any { it.profile_type == 100 }
//                val v200 = users.any { it.profile_type == 200 }
//                val v100_200 = v100 && v200
//
//                when {
//                    v100_200 -> {
//
//                    }
//                    v100 -> {
//
//                    }
//                    v200 -> {
//
//                    }
//
//                }
//
//                if (currentUser != null) {
//                    personInfo.add(currentUser)
                    adapter.submit(pannels.map { it.pannel })
//                    adapter.notifyDataSetChanged()

            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}