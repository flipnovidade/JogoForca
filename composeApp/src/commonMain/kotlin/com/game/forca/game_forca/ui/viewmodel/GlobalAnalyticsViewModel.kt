package com.game.forca.game_forca.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.game.forca.game_forca.service.AnalyticsService
import com.game.forca.game_forca.service.CrashlyticsService

class GlobalAnalyticsViewModel(
    private val analyticsService: AnalyticsService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {

    fun logAppStart() {
        analyticsService.logAppStart()
    }

    fun logScreenView(screenName: String) {
        analyticsService.logScreenView(screenName)
    }

    fun logClick(elementName: String) {
        analyticsService.logClick(elementName)
    }

    fun recordException(e: Throwable) {
        crashlyticsService.recordException(e)
    }
}
