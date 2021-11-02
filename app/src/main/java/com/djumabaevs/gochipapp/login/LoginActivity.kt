package com.djumabaevs.gochipapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.djumabaevs.gochipapp.MainActivity
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.PhoneAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        var btnToDrawer = findViewById<Button>(R.id.go_to_drawer)
//        btnToDrawer.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//        }
    }
}