package com.frontmatic.scrobbleview.data.source.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.frontmatic.scrobbleview.data.source.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreOperationsImpl(context: Context): DataStoreOperations {
    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
        val USER_CHANGED = booleanPreferencesKey("user_changed")
    }

    private val dataStore = context.dataStore

    override suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
        }
    }

    override suspend fun saveUserChanged(userChanged: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_CHANGED] = userChanged
        }
    }


    override fun getUsername(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.USERNAME] ?: ""
            }
    }

    override fun getUserChanged(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.USER_CHANGED] ?: false
            }
    }
}