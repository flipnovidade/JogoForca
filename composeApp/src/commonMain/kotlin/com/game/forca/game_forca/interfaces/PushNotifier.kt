package com.game.forca.game_forca.interfaces

interface PushNotifier {
    suspend fun initialize()
    suspend fun getToken(callback: (String?) -> Unit)
}