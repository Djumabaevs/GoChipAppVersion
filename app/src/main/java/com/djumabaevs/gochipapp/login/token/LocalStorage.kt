package com.djumabaevs.gochipapp.login.token

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
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

object LocalStorage {
    private fun pref(context: Context) =
        context.getSharedPreferences("SETTING_STORAGE_NAME", Context.MODE_PRIVATE)

    private fun editString(context: Context, key: String, value: String?) = pref(context).edit {
        putString(key, value)
    }

    private fun getString(context: Context, key: String, default: String?) =
        pref(context).getString(key, default) ?: default

    private fun editBoolean(context: Context, key: String, value: Boolean) = pref(context).edit {
        putBoolean(key, value)
    }

    private fun getBoolean(context: Context, key: String, default: Boolean) =
        pref(context).getBoolean(key, default)

    fun setToken(context: Context, token: String) = editString(context, ACCESS_TOKEN, token)

    fun getToken(context: Context) = getString(context, ACCESS_TOKEN, DEFAULT_TOKEN_VALUE)

    fun deleteToken(context: Context) {
        editString(context, ACCESS_TOKEN, DEFAULT_TOKEN_VALUE)
    }

    fun isAuthorized(context: Context) =
        getString(context, ACCESS_TOKEN, DEFAULT_TOKEN_VALUE) != DEFAULT_TOKEN_VALUE

    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val DEFAULT_TOKEN_VALUE = null
}