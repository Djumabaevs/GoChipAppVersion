package com.djumabaevs.gochipapp.login.newLogin


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toDeferred
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.login.LoginActivity
import com.djumabaevs.gochipapp.pannels.PannelActivity
import com.djumabaevs.gochipapp.util.CustomDialogStatusFragment
import com.djumabaevs.gochipapp.util.LocaleHelper
import com.djumabaevs.gochipapp.vets.VetActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import com.djumabaevs.gochipapp.MainActivity

import android.widget.ArrayAdapter



import android.widget.Spinner
import dagger.hilt.android.AndroidEntryPoint


/**
 * Login Screen of the application.
 */
@Suppress("DEPRECATION")
class NewLoginActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private var mContext: Context? = null

    fun getContext(): Context? {
        return mContext
    }

    fun setContext(mContext: Context?) {
        this.mContext = mContext
    }

    private var spinner: Spinner? = null
    private val paths = arrayOf("Choose language", "German", "Spain")
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.AppTheme)  //default app theme
        }

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_new_login)

        switchtheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        spinner = findViewById<View>(R.id.spinner) as Spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@NewLoginActivity,
            android.R.layout.simple_spinner_item, paths
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapter
        spinner!!.setOnItemSelectedListener(this)

        firebaseAuth = FirebaseAuth.getInstance()

        // This is used to hide the status bar and make the login screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


//        tv_forgot_password.setOnClickListener(this)

        btn_login.setOnClickListener(this)

        tv_register.setOnClickListener(this)

        tv_otp_phone_login.setOnClickListener(this)

    //    button_lang.setOnClickListener(this)
    }

    /**
     * In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

//                R.id.tv_forgot_password -> {
//
//                    // Launch the forgot password screen when the user clicks on the forgot password text.
////                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
////                    startActivity(intent)
//                }

                R.id.btn_login -> {
                      logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@NewLoginActivity, NewRegisterActivity::class.java)
                    startActivity(intent)
                }

                R.id.tv_otp_phone_login -> {
                    val intent = Intent(this@NewLoginActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

//                R.id.button_lang -> {
//                    val languageToLoad = "de"
//                    val locale = Locale(languageToLoad)
//                    Locale.setDefault(locale)
//                    val config = Configuration()
//                    config.locale = locale
//                    mContext?.getResources()
//                        ?.updateConfiguration(config, mContext!!.getResources().getDisplayMetrics())
//                    LocaleHelper.setLocale(this, "de")
//                    val intent = Intent(this@NewLoginActivity, NewLoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(intent)
//                }
            }
        }
    }

    /**
     * A function to validate the login entries of a user.
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to Log-In. The user will be able to log in using the registered email and password with Firebase Authentication.
     */
    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            val emailSignedIn = firebaseAuth.currentUser?.email

            GlobalScope.launch {

                val checkForEmail = apolloClient(this@NewLoginActivity).query(
                    GetPersonsDataQuery()
                ).toDeferred().await()

                val emails = checkForEmail
                    .data?.ui_pannels_to_users?.mapNotNull {
                        it.person.person_email
                    } ?: emptyList()
                val passwords = checkForEmail
                    .data?.ui_pannels_to_users?.mapNotNull {
                        it.person.person_password
                    } ?: emptyList()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->



                        // Hide the progress dialog
                        hideProgressDialog()

                        if (task.isSuccessful) {
                            if (emails.contains(emailSignedIn)) {
                                val users =
                                    checkForEmail.data?.ui_pannels_to_users?.filter { it.person.person_email == emailSignedIn }
                                        ?: emptyList()

                                val v100 = users.any { it.profile_type == 100 }
                                val v200 = users.any { it.profile_type == 200 }
                                val v100_200 = v100 && v200

                                when {
                                    v100_200 -> {
                                        val intent = Intent(this@NewLoginActivity, ChooseProfileActivity::class.java)
                                        startActivity(intent)
                                        //      basicAlert()
//                                        val f = CustomDialogStatusFragment()
//                                        f.show(supportFragmentManager, "null")
                                    }
                                    v100 -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Redirecting to Owner profile...", Toast.LENGTH_LONG
                                        ).show()
                                        startActivity(
                                            Intent(
                                                this@NewLoginActivity,
                                                PannelActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                    v200 -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Redirecting to Vet profile...", Toast.LENGTH_LONG
                                        ).show()
                                        startActivity(
                                            Intent(
                                                this@NewLoginActivity,
                                                VetActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    this@NewLoginActivity,
                                    "Not found",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
//                        val intent = Intent(this@NewLoginActivity, PannelActivity::class.java)
//                        startActivity(intent)
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
        when (position) {
            0 -> {

            }
            1 -> {
                val languageToLoad = "de"
                val locale = Locale(languageToLoad)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                mContext?.getResources()
                    ?.updateConfiguration(config, mContext!!.getResources().getDisplayMetrics())
                LocaleHelper.setLocale(this, "de")
                    val intent = Intent(this@NewLoginActivity, NewLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
            }
            2 -> {
                val languageToLoad = "es"
                val locale = Locale(languageToLoad)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                mContext?.getResources()
                    ?.updateConfiguration(config, mContext!!.getResources().getDisplayMetrics())
                LocaleHelper.setLocale(this, "es")
                val intent = Intent(this@NewLoginActivity, NewLoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}