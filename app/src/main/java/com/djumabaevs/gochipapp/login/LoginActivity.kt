package com.djumabaevs.gochipapp.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.djumabaevs.gochipapp.*
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.ActivityLoginBinding
import com.djumabaevs.gochipapp.pannels.PannelActivity
import com.djumabaevs.gochipapp.vets.VetActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "MAIN_TAG"

    private lateinit var progressDialog: ProgressDialog

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Redirecting to Owner profile...", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@LoginActivity, PannelActivity::class.java))
        finish()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Redirecting to Vet profile...", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@LoginActivity, VetActivity::class.java))
        finish()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Bye bye!", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.phoneLl.visibility = View.VISIBLE
        binding.codeLl.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                  signInWithPhoneAuthCredential(phoneAuthCredential)

                Log.d(TAG, "onVerificationCompleted: $phoneAuthCredential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent: $verificationId")
                mVerificationId = verificationId
                forceResendingToken = token
                progressDialog.dismiss()

                Log.d(TAG, "onCodeSent: $verificationId")

                binding.phoneLl.visibility = View.GONE
                binding.codeLl.visibility = View.VISIBLE
                Toast.makeText(this@LoginActivity, "Verification code sent...", Toast.LENGTH_SHORT).show()
                binding.codeSentDescriptionTv.text =
                    "Please type the verification code we sent to ${binding.phoneEt.text.toString().trim()}"
            }

        }

        binding.btnContinue.setOnClickListener {
            val phone = binding.phoneEt.text.toString().trim()
            if(TextUtils.isEmpty(phone)) {
                Toast.makeText(this@LoginActivity, "Please enter phone number", Toast.LENGTH_SHORT).show()
            } else {
                startPhoneNumberVerification(phone)
            }
        }

        binding.resendCodeTv.setOnClickListener {
            val phone = binding.phoneEt.text.toString().trim()
            if(TextUtils.isEmpty(phone)) {
                Toast.makeText(this@LoginActivity, "Please enter phone number", Toast.LENGTH_SHORT).show()
            } else {
                resendVerificationCode(phone, forceResendingToken)
            }
        }

        binding.btnCodeSubmit.setOnClickListener {
            val code = binding.codeEt.text.toString().trim()
            if(TextUtils.isEmpty(code)) {
                Toast.makeText(this@LoginActivity, "Please enter verification code", Toast.LENGTH_SHORT).show()
            } else {
                verifyPhoneNumberWithCode(mVerificationId, code)
            }
        }

//        binding.btnSkip.setOnClickListener {
////            supportFragmentManager.commit {
////                replace(R.id.login_main, PanelFragment())
////                setReorderingAllowed(true)
////                addToBackStack(null)
////            }
////            startActivity(Intent(this, PannelActivity::class.java))
//        }
    }

    private fun startPhoneNumberVerification(phone: String) {
        progressDialog.setMessage("Verifying phone number...")
        progressDialog.show()

        Log.d(TAG, "startPhoneNumberVerification: $phone")

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBacks!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(phone: String, token: PhoneAuthProvider.ForceResendingToken?) {
        progressDialog.setMessage("Resending code...")
        progressDialog.show()

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBacks!!)
            .setForceResendingToken(token!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        progressDialog.setMessage("Verifying code...")
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

            signInWithPhoneAuthCredential(credential)

    }


    private  fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
       GlobalScope.launch {
           progressDialog.setMessage("Loggin in")

           val phoneId: String? = null
           val resName = apolloClient(this@LoginActivity).query(
               GetVetPersonByPhoneQuery(phone = Input.fromNullable(phoneId))
           ).toDeferred().await()

           val res = apolloClient(this@LoginActivity).query(
               GetPhoneVetByIdQuery()
           ).toDeferred().await()


           val personUid = apolloClient(this@LoginActivity).query(
               GetVetPersonByPhoneQuery()
           ).toDeferred().await()

           val checkVetOrNot = apolloClient(this@LoginActivity).query(
               GetPersonsDataQuery()
           ).toDeferred().await()

           firebaseAuth.signInWithCredential(credential)
               .addOnSuccessListener {

                   val phone = firebaseAuth.currentUser?.phoneNumber?.replaceFirst("+","")

                   val phones = checkVetOrNot
                       .data?.ui_pannels_to_users?.mapNotNull {
                           it.person.person_phone
                       } ?: emptyList()


                   if(phones.contains(phone)) {
                       val users = checkVetOrNot.data?.ui_pannels_to_users?.filter { it.person.person_phone == phone } ?: emptyList()

                       val v100 = users.any { it.profile_type == 100 }
                       val v200 = users.any { it.profile_type == 200 }
                       val v100_200 = v100 && v200

                       when {
                           v100_200 -> {
                            basicAlert()
                           }
                           v100 -> {
                               Toast.makeText(applicationContext,
                                   "Redirecting to Owner profile...", Toast.LENGTH_LONG).show()
                               startActivity(Intent(this@LoginActivity, PannelActivity::class.java))
                               finish()
                           }
                           v200 -> {
                               Toast.makeText(applicationContext,
                                   "Redirecting to Vet profile...", Toast.LENGTH_LONG).show()
                               startActivity(Intent(this@LoginActivity, VetActivity::class.java))
                               finish()
                           }
                       }


                   } else {
                       Toast
                           .makeText(this@LoginActivity, "Hi! We could not find your number in our database. PLease contact Dmitriy from Cyprus!" +
                                   "Please wait, you will be directed to special screen!",
                               Toast.LENGTH_LONG).show()

                   }
               }
               .addOnFailureListener { e ->
                   progressDialog.dismiss()
                   Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()
               }
       }
    }

//    private suspend fun makeLoginRequest(value: String) {
//        val phoneId: String? = null
//        val res = apolloClient(this@LoginActivity).query(
//       GetVetPersonByPhoneQuery(phone = Input.fromNullable(phoneId))
//    ).toDeferred().await()
//
//    binding.recyclerview.apply {
////        adapter = res.data?.persons?.firstOrNull()?.person_name { LoginAdapter(it) }
//        adapter = res.data?.persons.let {
//            if(res.data?.persons?.firstOrNull()?.persons_vets?.firstOrNull()?.vet != null) {
//                LoginAdapter(it!!)
//            } else {
//                LoginAdapter(it!!)
//            }
//        }
//    }
//
//    }
//
//    fun getLoginData(view: View) {
//    coroutineScope.launch {
//        makeLoginRequest(binding.editText.text.toString())
//    }
//}
    fun basicAlert(){
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choosing Vet or Owner")
            setMessage("Please choose profile type")
            setPositiveButton("Owner", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton("Vet", negativeButtonClick)
            setNeutralButton("Cancel", neutralButtonClick)
            show()
        }
    }
}







