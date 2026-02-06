package com.game.forca.game_forca.data

import kotlinx.serialization.Serializable

@Serializable
data class WordItem(
    val id: Int,
    val category: String,
    val word: String,
    val assay: String
)