package com.game.forca.game_forca

data class AppVersion(
    val name: String,
    val build: String
)

expect fun getAppVersion(): AppVersion
