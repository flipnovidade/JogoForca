package com.game.forca.game_forca.crashlytics

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics

interface CrashlyticsService {
    fun logMessage(message: String)
    fun recordException(e: Throwable)
    fun setCustomKey(key: String, value: String)
    fun setUserId(id: String)
}

class CrashlyticsServiceImpl : CrashlyticsService {
    override fun logMessage(message: String) {
        Firebase.crashlytics.log(message)
    }

    override fun recordException(e: Throwable) {
        Firebase.crashlytics.recordException(e)
    }

    override fun setCustomKey(key: String, value: String) {
        Firebase.crashlytics.setCustomKey(key, value)
    }

    override fun setUserId(id: String) {
        Firebase.crashlytics.setUserId(id)
    }
}
