package com.game.forca.game_forca

import com.game.forca.game_forca.BuildConfig

actual fun getAppVersion(): AppVersion {
    return AppVersion(
        name = BuildConfig.VERSION_NAME,
        build = BuildConfig.VERSION_CODE.toString()
    )
}
