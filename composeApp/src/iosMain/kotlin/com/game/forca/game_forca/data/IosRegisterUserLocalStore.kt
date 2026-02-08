package com.game.forca.game_forca.data

import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

class IosRegisterUserLocalStore : RegisterUserLocalStore {
    private val defaults = NSUserDefaults.standardUserDefaults()
    private val json = Json { ignoreUnknownKeys = true }
    private val userKey = "register_user_json"
    private val positionKey = "position_started_word"
    private val numberFileKey = "number_file_words"

    override suspend fun saveUser(user: RegisterUserItem) {
        val payload = json.encodeToString(RegisterUserItem.serializer(), user)
        defaults.setObject(payload, forKey = userKey)
    }

    override suspend fun getUser(): RegisterUserItem? {
        val payload = defaults.stringForKey(userKey) ?: return null
        return runCatching {
            json.decodeFromString(RegisterUserItem.serializer(), payload)
        }.getOrNull()
    }

    override suspend fun clear() {
        defaults.removeObjectForKey(userKey)
        defaults.removeObjectForKey(positionKey)
        defaults.removeObjectForKey(numberFileKey)
    }

    override suspend fun saveGameProgress(positionStartedWord: Int, numberFileWords: Int) {
        defaults.setInteger(positionStartedWord.toLong(), forKey = positionKey)
        defaults.setInteger(numberFileWords.toLong(), forKey = numberFileKey)
    }

    override suspend fun getGameProgress(): Pair<Int, Int>? {
        if (defaults.objectForKey(positionKey) == null ||
            defaults.objectForKey(numberFileKey) == null) {
            return 0 to 0
        }
        val position = defaults.integerForKey(positionKey).toInt()
        val numberFile = defaults.integerForKey(numberFileKey).toInt()
        return position to numberFile
    }
}
