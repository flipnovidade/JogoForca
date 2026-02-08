package com.game.forca.game_forca.data

interface RegisterLoginRepository {
    suspend fun findByEmailPassword(email: String, password: String): RegisterUserItem?
    suspend fun updateUser(registerUserItem: RegisterUserItem)
}
