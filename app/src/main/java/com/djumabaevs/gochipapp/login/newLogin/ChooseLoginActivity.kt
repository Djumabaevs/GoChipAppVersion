package com.djumabaevs.gochipapp.login.newLogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityChooseLoginBinding

class ChooseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChooseLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginKeycloak.setOnClickListener {

        }

        binding.loginEmailAuth.setOnClickListener {

        }
    }
}