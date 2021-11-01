package com.djumabaevs.gochipapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.djumabaevs.gochipapp.MainActivity
import com.djumabaevs.gochipapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btnToDrawer = findViewById<Button>(R.id.go_to_drawer)
        btnToDrawer.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}