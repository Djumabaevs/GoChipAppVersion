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
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import com.djumabaevs.gochipapp.vets.VetActivity
import kotlinx.android.synthetic.main.activity_new_login.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.channels.Channel

class VetPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetPanelBinding

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

        val adapter = PersonAdapter(listOf())
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this)
        binding.detailsRecycler.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        val people = mutableListOf<GetPersonsDataQuery.Ui_pannels_to_user>()

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val response = try {
                    apolloClient(this@VetPanelActivity).query(GetPersonsDataQuery()).await()
                } catch (e: ApolloException) {
                    return@launchWhenResumed
                }

                binding.progressBar.visibility = View.GONE

                val newPeople = response.data?.ui_pannels_to_users


                if (newPeople != null) {
                    people.addAll(newPeople)
                    adapter.notifyDataSetChanged()
                }
                //    petUid = response.data?.pets?.get(0)?.pet_uid.toString()


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

//        binding.toolbar.setOnClickListener {
//            startActivity(intentPannel)
//        }

      //  binding.vetName.text = personsNameGet
      //  binding.vetName.text = panelInfo

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}