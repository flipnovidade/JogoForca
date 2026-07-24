package com.game.forca.game_forca.service

interface AnalyticsService {
    fun logEvent(name: String, params: Map<String, Any>? = null)
    fun setUserProperty(name: String, value: String?)
    fun setUserId(userId: String?)
    fun logClick(elementName: String)
    fun logScreenView(screenName: String)
    fun logAppStart()
}
