package com.game.forca.game_forca.service

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics

class CrashlyticsServiceImpl : CrashlyticsService {

    private val crashlytics by lazy { Firebase.crashlytics }

    override fun log(message: String) {
        runCatching {
            crashlytics.log(message)
        }
    }

    override fun recordException(throwable: Throwable) {
        runCatching {
            crashlytics.recordException(throwable)
        }
    }

    override fun setCustomKey(key: String, value: String) {
        runCatching {
            crashlytics.setCustomKey(key, value)
        }
    }

    override fun setUserId(userId: String) {
        runCatching {
            crashlytics.setUserId(userId)
        }
    }
}
