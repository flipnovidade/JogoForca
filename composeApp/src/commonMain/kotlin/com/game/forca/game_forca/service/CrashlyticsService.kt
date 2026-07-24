package com.game.forca.game_forca.service

interface CrashlyticsService {
    fun log(message: String)
    fun recordException(throwable: Throwable)
    fun setCustomKey(key: String, value: String)
    fun setUserId(userId: String)
}
