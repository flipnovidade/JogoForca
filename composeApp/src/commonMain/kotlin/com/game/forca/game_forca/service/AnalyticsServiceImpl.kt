package com.game.forca.game_forca.service

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

class AnalyticsServiceImpl : AnalyticsService {

    private val analytics by lazy { Firebase.analytics }

    override fun logEvent(name: String, params: Map<String, Any>?) {
        runCatching {
            if (params != null) {
                analytics.logEvent(name, params)
            } else {
                analytics.logEvent(name)
            }
        }
    }

    override fun setUserProperty(name: String, value: String?) {
        runCatching {
            analytics.setUserProperty(name = name, value = value ?: "")
        }
    }

    override fun setUserId(userId: String?) {
        runCatching {
            analytics.setUserId(id = userId ?: "")
        }
    }

    override fun logClick(elementName: String) {
        logEvent("click_element", mapOf("element_name" to elementName))
    }

    override fun logScreenView(screenName: String) {
        logEvent("screen_view", mapOf("screen_name" to screenName))
    }

    override fun logAppStart() {
        logEvent("app_start")
    }
}
