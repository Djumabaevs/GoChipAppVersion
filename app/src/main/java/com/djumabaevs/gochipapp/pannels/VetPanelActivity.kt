package com.djumabaevs.gochipapp.pannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding

class VetPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVetPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}