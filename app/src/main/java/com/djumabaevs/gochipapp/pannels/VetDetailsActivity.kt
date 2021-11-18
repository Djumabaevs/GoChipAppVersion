package com.djumabaevs.gochipapp.pannels

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.toDeferred
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class VetDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetPanelBinding

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val vetName = intent.getStringExtra("vetName")

        supportActionBar?.apply {
            title = vetName
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        var adapter = PersonAdapter(listOf())
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this)
        binding.detailsRecycler.adapter = adapter

    //    binding.progressBar.visibility = View.VISIBLE


        val channel = Channel<Unit>(Channel.CONFLATED)

//        coroutineScope.launch {
//            makeLoginRequest()
//        }

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

//    private suspend fun makeLoginRequest() {
//        val res = apolloClient(this@PetIDActivity).query(
//            GetPersonsDataQuery()
//        ).toDeferred().await()
//
//        binding.detailsRecycler.apply {
//            adapter = res.data?.ui_pannels_to_users?.let {
//                PersonAdapter(it)
//            }
//            binding.progressBar.visibility = View.GONE
//        }
//
//    }
}