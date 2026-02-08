package com.game.forca.game_forca.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "register_user_store")

class AndroidRegisterUserLocalStore(
    private val context: Context
) : RegisterUserLocalStore {
    private val json = Json { ignoreUnknownKeys = true }
    private val userKey = stringPreferencesKey("register_user_json")
    private val positionKey = stringPreferencesKey("position_started_word")
    private val numberFileKey = stringPreferencesKey("number_file_words")

    override suspend fun saveUser(user: RegisterUserItem) {
        val payload = json.encodeToString(RegisterUserItem.serializer(), user)
        context.dataStore.edit { prefs ->
            prefs[userKey] = payload
        }
    }

    override suspend fun getUser(): RegisterUserItem? {
        val prefs: Preferences = context.dataStore.data.first()
        val payload = prefs[userKey] ?: return null
        return runCatching {
            json.decodeFromString(RegisterUserItem.serializer(), payload)
        }.getOrNull()
    }

    override suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.remove(userKey)
            prefs.remove(positionKey)
            prefs.remove(numberFileKey)
        }
    }

    override suspend fun saveGameProgress(positionStartedWord: Int, numberFileWords: Int) {
        context.dataStore.edit { prefs ->
            prefs[positionKey] = positionStartedWord.toString()
            prefs[numberFileKey] = numberFileWords.toString()
        }
    }

    override suspend fun getGameProgress(): Pair<Int, Int>? {
        val prefs: Preferences = context.dataStore.data.first()
        val position = prefs[positionKey]?.toIntOrNull() ?: 0
        val numberFile = prefs[numberFileKey]?.toIntOrNull() ?: 0
        return position to numberFile
    }
}
