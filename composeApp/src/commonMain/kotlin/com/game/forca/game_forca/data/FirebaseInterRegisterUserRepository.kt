package com.game.forca.game_forca.data

interface FirebaseInterRegisterUserRepository {
    suspend fun fetchFirstUser(): RegisterUserItem?
    suspend fun saveUser(registerUserItem: RegisterUserItem): String
}
