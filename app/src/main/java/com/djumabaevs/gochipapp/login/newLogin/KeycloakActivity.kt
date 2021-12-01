package com.djumabaevs.gochipapp.login.newLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityKeycloakBinding

class KeycloakActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityKeycloakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityKeycloakBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val username = binding.etEmail.text
        val password = binding.etPassword.text
        val grantType = binding.etGrant.text
        val clientId = binding.etClient.text

        val token = viewModel.myToken.value?.body()?.accessToken

        binding.btnLogin.setOnClickListener {
            viewModel.getJWTToken(username.toString(), password.toString(), grantType.toString(), clientId.toString())
            viewModel.myToken.observe(this) { response ->
                response?.let {
                    Log.d("Response: ", response.body()?.accessToken.toString())
                    binding.textView.text = response.body()?.accessToken.toString()
                    if(response.isSuccessful) {
                        LocalStorage.setToken(this, response?.body()?.accessToken!!)
                        val intent = Intent(this, DetailsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}