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

    fun getUserAccessToken(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("REFRESH_TOKEN", null);
    }

    fun setUserAccessToken(context: Context, userAccessToken: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putString("REFRESH_TOKEN", userAccessToken);
        }
    }
}