package com.game.forca.game_forca.data

interface RegisterUserRepository {
    suspend fun fetchFirstUser(): RegisterUserItem?
}
