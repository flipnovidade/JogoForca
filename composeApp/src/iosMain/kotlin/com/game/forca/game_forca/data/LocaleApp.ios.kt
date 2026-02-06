package com.game.forca.game_forca.data

import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

actual fun getSystemLocale(): String {
    val languages = NSLocale.preferredLanguages
    return if (languages.isNotEmpty()) {
        languages[0] as String
    } else {
        "en"
    }
}