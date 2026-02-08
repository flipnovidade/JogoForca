package com.game.forca.game_forca.data

interface RegisterUserLocalStore {
    suspend fun saveUser(registerUserItem: RegisterUserItem)
    suspend fun getUser(): RegisterUserItem?
}
