package com.game.forca.game_forca.service

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig

class RemoteConfigServiceImpl : RemoteConfigService {

    private val remoteConfig by lazy { Firebase.remoteConfig }

    override suspend fun fetchAndActivate(): Boolean {
        return runCatching {
            remoteConfig.fetchAndActivate()
        }.getOrDefault(false)
    }

    override fun getString(key: String): String {
        return runCatching {
            remoteConfig.getValue(key).asString()
        }.getOrDefault("")
    }

    override fun getBoolean(key: String): Boolean {
        return runCatching {
            remoteConfig.getValue(key).asBoolean()
        }.getOrDefault(false)
    }

    override fun getLong(key: String): Long {
        return runCatching {
            remoteConfig.getValue(key).asLong()
        }.getOrDefault(0L)
    }

    override fun getDouble(key: String): Double {
        return runCatching {
            remoteConfig.getValue(key).asDouble()
        }.getOrDefault(0.0)
    }
}
