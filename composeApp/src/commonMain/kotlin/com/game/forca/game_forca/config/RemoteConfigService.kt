package com.game.forca.game_forca.config

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface RemoteConfigService {
    suspend fun fetchAndActivate()
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String
}

class RemoteConfigServiceImpl : RemoteConfigService {
    override suspend fun fetchAndActivate() {
        try {
            Firebase.remoteConfig.fetchAndActivate()
        } catch (e: Exception) {
            println("Failed to fetch Remote Config: ${e.message}")
        }
    }

    override fun getBoolean(key: String): Boolean {
        return try {
            Firebase.remoteConfig.getValue(key).asBoolean()
        } catch (e: Exception) {
            false
        }
    }

    override fun getString(key: String): String {
        return try {
            Firebase.remoteConfig.getValue(key).asString()
        } catch (e: Exception) {
            ""
        }
    }
}
