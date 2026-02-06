package com.game.forca.game_forca.data

actual fun closeApp() {
    val activity = CurrentActivityHolder.activity
    activity?.finish()
}