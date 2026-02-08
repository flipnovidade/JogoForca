package com.game.forca.game_forca.data

interface RegisterUserLocalStore {
    suspend fun saveUser(registerUserItem: RegisterUserItem)
    suspend fun getUser(): RegisterUserItem?
    suspend fun clear()
    suspend fun saveGameProgress(positionStartedWord: Int, numberFileWords: Int)
    suspend fun getGameProgress(): Pair<Int, Int>?
}
