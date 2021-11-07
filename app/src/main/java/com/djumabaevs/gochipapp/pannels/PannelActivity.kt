package com.djumabaevs.gochipapp.pannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityPannelBinding

class PannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listOfPersonData = ArrayList<GetPersonsDataQuery>()

    }
}