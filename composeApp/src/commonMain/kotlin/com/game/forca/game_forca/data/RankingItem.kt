package com.game.forca.game_forca.data

import kotlinx.serialization.Serializable

@Serializable
data class RankingItem(
    val position: Int = 0,
    val name: String = "",
    val score: Int = 0,
    val email: String = "",
    val pass: String = "",
    val keyPush: String = ""
)