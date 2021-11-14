package com.djumabaevs.gochipapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}