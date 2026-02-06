package com.game.forca.game_forca

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform