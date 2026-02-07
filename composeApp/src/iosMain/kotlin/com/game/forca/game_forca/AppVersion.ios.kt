package com.game.forca.game_forca

import platform.Foundation.NSBundle

actual fun getAppVersion(): AppVersion {
    val info = NSBundle.mainBundle.infoDictionary
    val name = info?.get("CFBundleShortVersionString") as? String ?: "0.0"
    val build = info?.get("CFBundleVersion") as? String ?: "0"
    return AppVersion(name = name, build = build)
}
