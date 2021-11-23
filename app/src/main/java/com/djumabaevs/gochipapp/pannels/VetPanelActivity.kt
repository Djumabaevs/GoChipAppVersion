package com.djumabaevs.gochipapp.pannels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.*
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import com.djumabaevs.gochipapp.pets.PersonsPetsAdapter
import com.djumabaevs.gochipapp.util.VET_TYPE
import com.djumabaevs.gochipapp.vets.VetActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_login.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class VetPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetPanelBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.AppTheme)  //default app theme
        }
        binding = ActivityVetPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        val intentPannel = Intent(this, PannelActivity::class.java)
        val intentVet = Intent(this, VetActivity::class.java)
        setSupportActionBar(binding.toolbar)

        val vetName = intent.getStringExtra("vetName")

        supportActionBar?.apply {
            title = vetName
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val adapter = PersonsPetsAdapter()
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.detailsRecycler.adapter = adapter
        val channel = Channel<Unit>(Channel.CONFLATED)

//        coroutineScope.launch {
//            makeLoginRequest()
//        }

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }


        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val response = try {
                    apolloClient(this@VetPanelActivity).query(GetPersonPetQuery()).await()
                } catch (e: ApolloException) {
                    return@launchWhenResumed
                }

                binding.progressBar.visibility = View.GONE
                val emailSignedIn = firebaseAuth.currentUser?.email

                val personData = response.data?.persons_pets?.first {
                    it.person.person_email == emailSignedIn
                }

                personData?.person?.persons_pets?.let {
                    adapter.submitData(it)
                }

                val personEmail = response.data?.persons_pets?.firstOrNull()?.person?.person_email
                val newPets = response.data?.persons_pets?.filterNotNull()

            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

//    private suspend fun makeLoginRequest() {
//        val res = apolloClient(this@VetPanelActivity).query(
//            GetPersonPetQuery()
//        ).toDeferred().await()
//
//        val emailSignedIn = firebaseAuth.currentUser?.email
//        val petName = intent.getStringExtra("petName")
//        val checkEmail = res.data?.persons_pets?.map{
//            it.person.person_email
//        } ?: emptyList()
//
//        if(checkEmail.contains(emailSignedIn)) {
//            binding.detailsRecycler.apply {
//                adapter = res.data?.persons_pets?.let {
//                    PersonsPetsAdapter(it)
//                }
//                binding.progressBar.visibility = View.GONE
//            }
//        }
//
//    }
}