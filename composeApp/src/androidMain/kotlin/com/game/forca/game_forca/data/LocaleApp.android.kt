package com.game.forca.game_forca.data

actual fun getSystemLocale(): String {
    return java.util.Locale.getDefault().toString()
}