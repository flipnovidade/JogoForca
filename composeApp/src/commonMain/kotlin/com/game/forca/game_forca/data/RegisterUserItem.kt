package com.game.forca.game_forca.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserItem(
    val name: String = "",
    val email: String = "",
    val score: Int = 0,
    val password: String = "",
    val keyForPush: String = ""
)
