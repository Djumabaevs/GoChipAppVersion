package com.djumabaevs.gochipapp.login.newLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityChooseLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChooseLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginKeycloak.setOnClickListener {
            val intent = Intent(this, KeycloakActivity::class.java)
            startActivity(intent)
        }

        binding.loginEmailAuth.setOnClickListener {
            val intent = Intent(this, NewLoginActivity::class.java)
            startActivity(intent)
        }
    }
}