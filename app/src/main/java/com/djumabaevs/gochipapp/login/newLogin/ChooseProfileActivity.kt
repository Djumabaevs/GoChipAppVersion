package com.djumabaevs.gochipapp.login.newLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.djumabaevs.gochipapp.MainActivity
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityChooseProfileBinding
import com.djumabaevs.gochipapp.pannels.PannelActivity
import com.djumabaevs.gochipapp.vets.VetActivity

class ChooseProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.AppTheme)  //default app theme
        }
        binding = ActivityChooseProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOwner.setOnClickListener {
            val intent = Intent(this, VetActivity::class.java)
            startActivity(intent)
        }
        binding.btnVet.setOnClickListener {
            val intent = Intent(this, PannelActivity::class.java)
            startActivity(intent)
        }
        binding.btnShelter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}