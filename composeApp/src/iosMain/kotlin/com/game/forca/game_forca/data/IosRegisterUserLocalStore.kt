package com.game.forca.game_forca.data

import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

class IosRegisterUserLocalStore : RegisterUserLocalStore {
    private val defaults = NSUserDefaults.standardUserDefaults()
    private val json = Json { ignoreUnknownKeys = true }
    private val userKey = "register_user_json"

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
}
