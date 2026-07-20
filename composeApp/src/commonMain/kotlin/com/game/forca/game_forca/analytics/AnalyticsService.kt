package com.game.forca.game_forca.analytics

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

interface AnalyticsService {
    fun logAppStart()
    fun logScreenView(screenName: String)
    fun logClick(elementName: String)
}

class AnalyticsServiceImpl : AnalyticsService {
    override fun logAppStart() {
        Firebase.analytics.logEvent("app_start")
    }

    override fun logScreenView(screenName: String) {
        Firebase.analytics.logEvent("screen_view", mapOf("screen_name" to screenName))
    }

    override fun logClick(elementName: String) {
        Firebase.analytics.logEvent("click_event", mapOf("element_name" to elementName))
    }
}
