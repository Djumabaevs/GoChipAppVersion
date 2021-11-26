package com.djumabaevs.gochipapp.login.token

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class LocalStorage @Inject constructor(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFS_NAME
    )

    // App locale
    val getLocale: String
        get() {
            val localeFromStorage = sharedPreferences.getString(APP_LOCALE_CODE, null)
            return localeFromStorage ?: getDeviceOrDefaultLocale()
        }

    private fun getDeviceOrDefaultLocale(): String {
        val deviceLocaleCode = Locale.getDefault().language
        return if (AppLocale.isSupported(deviceLocaleCode)) {
            setLocale(deviceLocaleCode)
            deviceLocaleCode
        } else {
            val defaultLocaleCode = DEFAULT_LOCALE.code
            setLocale(defaultLocaleCode)
            defaultLocaleCode
        }
    }

    fun setLocale(localeCode: String) {
        sharedPreferences.edit()?.apply {
            putString(APP_LOCALE_CODE, localeCode)
            commit()
        }
    }

    // FCM token
    val getFCMToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[FCM_TOKEN]
    }

    suspend fun setFCMToken(fcmToken: String) {
        context.dataStore.edit { prefs ->
            prefs[FCM_TOKEN] = fcmToken
        }
    }

    // User data
    suspend fun setAnonymToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ANONYM_TOKEN] = token
        }
    }

    val getAnonymToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[ANONYM_TOKEN]
    }

    suspend fun setUserToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_TOKEN] = token
        }
    }

    val getUserToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_TOKEN]
    }

    suspend fun setUserName(firstName: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_FIRST_NAME] = firstName ?: ""
        }
    }

    val getUserName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_FIRST_NAME] ?: ""
    }

    suspend fun setUserPhone(phone: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_PHONE] = phone ?: ""
        }
    }

    val getUserPhone: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_PHONE] ?: ""
    }

    suspend fun setUserEmail(email: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL] = email ?: ""
        }
    }

    suspend fun setUserPassword(password: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_PASSWORD] = password ?: ""
        }
    }

    val getUserPassword: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_PASSWORD] ?: ""
    }

    val getUserEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_EMAIL] ?: ""
    }


    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs[USER_TOKEN] = ""
            prefs[USER_FIRST_NAME] = ""
            prefs[USER_PHONE] = ""
            prefs[USER_EMAIL] = ""
            prefs[USER_PASSWORD] = ""
        }
    }


    companion object {
        const val PREFS_NAME = "PREFS_USERS"

        const val APP_LOCALE_CODE = "language"
        private val FCM_TOKEN = stringPreferencesKey(name = "fcm_token")
        private val ANONYM_TOKEN = stringPreferencesKey(name = "PREFS_ANONYM")

        private val USER_TOKEN = stringPreferencesKey(name = "token")
        private val USER_FIRST_NAME = stringPreferencesKey(name = "firstName")
        private val USER_PHONE = stringPreferencesKey(name = "phone")
        private val USER_EMAIL = stringPreferencesKey(name = "email")
        private val USER_PASSWORD = stringPreferencesKey(name = "password")


    }
}