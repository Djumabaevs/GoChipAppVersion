package com.djumabaevs.gochipapp.pannels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityVetPanelBinding
import com.djumabaevs.gochipapp.vets.VetActivity
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

        supportActionBar?.apply {
            title = "Toolbar Back Button"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setOnClickListener {
            startActivity(intentPannel)
        }
        var vetName = intent.getStringExtra("vetName")
        binding.vetName.text = vetName

    }
}