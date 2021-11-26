package com.djumabaevs.gochipapp.login.token

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.databinding.ObservableBoolean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

//class UserData @Inject constructor(
//    private val context: Context,
//    private val localStorage: LocalStorage,
//) {
//
//    private var userCache: User? = null
//    private var initialized : Boolean = false
//
//    val isLoggedIn = ObservableBoolean()
//
//    suspend fun getFromLocalStorage() {
//        if (initialized) {
//            return
//        }
//        val name = localStorage.getUserName.first()
//        val token = localStorage.getUserToken.first()
//        val email = localStorage.getUserEmail.first()
//        val phone = localStorage.getUserPhone.first()
//        //Added
//        val password = localStorage.getUserPassword.first()
//
//        if (token.isNullOrEmpty()) {
//            userCache = null
//            isLoggedIn.set(false)
//        } else {
//            //Added password
//            userCache = User(firstName = name, token = token, email = email, phone = phone, password = password)
//            isLoggedIn.set(true)
//        }
//
//        initialized = true
//    }
//
//    @SuppressLint("HardwareIds")
//    suspend fun getTokenHeader(): Map<String, String> {
//        val map = HashMap<String, String>()
//        if (isLoggedIn()) {
//            map[HEADER_AUTH] = "Bearer " + getUserToken()
//        } else {
//            map[HEADER_ANONYM_TOKEN] = localStorage.getAnonymToken.first() ?: generateAnonymToken()
//        }
//
//        return map
//    }
//
//    suspend fun getFCMToken(): String? {
//        return localStorage.getFCMToken.first()
//    }
//
//    @SuppressLint("HardwareIds")
//    private suspend fun generateAnonymToken(): String {
//        val anonymToken = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
//        localStorage.setAnonymToken(anonymToken)
//        return anonymToken
//    }
//
//    suspend fun setData(user: User) {
//        userCache = user
//        isLoggedIn.set(true)
//
//        localStorage.setUserToken(user.token)
//        localStorage.setUserName(user.firstName)
//        localStorage.setUserPhone(user.phone)
//        localStorage.setUserEmail(user.email)
//        //Added
//        localStorage.setUserPassword(user.password)
//    }
//
//    suspend fun setUserName(name: String) {
//        userCache?.firstName = name
//
//        localStorage.setUserName(name)
//    }
//
//    suspend fun setUserPhone(phone: String) {
//        userCache?.phone = phone
//
//        localStorage.setUserPhone(phone)
//    }
//
//    fun getUser(): User? {
//        return userCache
//    }
//
//    fun isLoggedIn(): Boolean {
//        return isLoggedIn.get()
//    }
//
//    suspend fun getUserToken(): String? {
//        if (userCache != null) {
//            return userCache!!.token
//        }
//
//        return localStorage.getUserToken.first()
//    }
//
//    suspend fun clearUserData() {
//        userCache = null
//        withContext(Dispatchers.Main) {
//            isLoggedIn.set(false)
//        }
//        localStorage.clearUserData()
//    }
//
//    companion object {
//        private const val HEADER_ANONYM_TOKEN = "anonymtoken"
//        private const val HEADER_AUTH = "Authorization"
//    }
//}