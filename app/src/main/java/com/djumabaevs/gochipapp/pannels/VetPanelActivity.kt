package com.djumabaevs.gochipapp.pannels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import com.djumabaevs.gochipapp.vets.VetActivity
import kotlinx.android.synthetic.main.activity_new_login.*
import kotlinx.android.synthetic.main.app_bar_main.*

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

        supportActionBar?.apply {
            title = vetName
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setOnClickListener {
            startActivity(intentPannel)
        }

        binding.vetName.text = personsNameGet

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}