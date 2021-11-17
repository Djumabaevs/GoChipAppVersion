package com.djumabaevs.gochipapp.pannels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.GetVetPersonByPhoneQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import com.djumabaevs.gochipapp.login.LoginAdapter
import com.djumabaevs.gochipapp.vets.VetActivity
import kotlinx.android.synthetic.main.activity_new_login.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class VetPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetPanelBinding

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentPannel = Intent(this, PannelActivity::class.java)
        val intentVet = Intent(this, VetActivity::class.java)
        setSupportActionBar(binding.toolbar)

        val vetName = intent.getStringExtra("vetName")
        val personsNameGet = intent.getStringExtra("personName")
        val panelInfo = intent.getStringExtra("panelType")

        supportActionBar?.apply {
            title = vetName
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        var adapter = PersonAdapter(listOf())
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this)
        binding.detailsRecycler.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        val people = mutableListOf<GetPersonsDataQuery>()

        val channel = Channel<Unit>(Channel.CONFLATED)

        coroutineScope.launch {
            makeLoginRequest(binding.testName.toString())
        }

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private suspend fun makeLoginRequest(value: String) {
        val res = apolloClient(this@VetPanelActivity).query(
            GetPersonsDataQuery()
        ).toDeferred().await()

        binding.detailsRecycler.apply {
            adapter = res.data?.ui_pannels_to_users?.let {
                    PersonAdapter(it)
            }
            binding.progressBar.visibility = View.GONE
        }

    }
}