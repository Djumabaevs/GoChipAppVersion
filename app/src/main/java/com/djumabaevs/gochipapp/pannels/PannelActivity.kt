package com.djumabaevs.gochipapp.pannels

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
import com.djumabaevs.gochipapp.GetAllPetsQuery
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityPannelBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel

class PannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannelBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val personInfo = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
        val adapter = VetAdapter(listOf(), null)
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
            var profileType: Int = 100
            for (item in channel) {
                val response = try {
                    apolloClient(this@PannelActivity)
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

                val mainQuery = apolloClient(this@PannelActivity).query(
                    GetPersonsDataQuery()
                ).toDeferred().await()
                val query2 = apolloClient(this@PannelActivity).query(
                    GetAllPetsQuery()
                ).toDeferred().await()

                val personName = query2.data?.persons_pets?.firstOrNull()?.person?.person_name
                val petname = query2.data?.persons_pets?.filter{ it.person.person_name == personName }?.first()
                val pannels = mainQuery.data?.ui_pannels_to_users?.filter {
                    (it.person.person_phone == phone &&
                            it.profile_type == 200) || (it.person.person_email == emailSignedIn && it.profile_type == 200)
                }?.sortedBy { it.pannel_order  } ?: emptyList()

//                val checkVetOrNot = apolloClient(this@PannelActivity).query(
//                    GetPersonsDataQuery()
//                ).toDeferred().await()
//
//                val phones = checkVetOrNot
//                    .data?.ui_pannels_to_users?.mapNotNull {
//                        it.person.person_phone
//                    } ?: emptyList()
//
//                val veterinars = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
//                val people = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
//
//                val personPhone = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()
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
//                val usersProfile = checkVetOrNot.data?.ui_pannels_to_users?.filter { it.profile_type == 100 } ?: emptyList()
//
//        //        val newPersonData = response.data?.ui_pannels_to_users
//
//                val users = checkVetOrNot.data?.ui_pannels_to_users?.filter { it.person.person_phone == phone } ?: emptyList()

//                if ( veterinars != null) {
//                    personInfo.addAll(users.intersect(veterinars))
//                    adapter.notifyDataSetChanged()
//                }



               adapter.submit(pannels.map { it.pannel }, petname)


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}