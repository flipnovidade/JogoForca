package com.game.forca.game_forca.data

interface RegisterUserLocalStore {
    suspend fun saveUser(user: RegisterUserItem)
    suspend fun getUser(): RegisterUserItem?
}
