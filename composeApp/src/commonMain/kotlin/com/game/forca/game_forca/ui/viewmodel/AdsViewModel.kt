package com.game.forca.game_forca.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.forca.game_forca.service.RemoteConfigService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdsConfig(
    val showBannerBottom: Boolean = true,
    val bannerBottomAdUnitId: String = "",
    val showInterstitial: Boolean = true,
    val interstitialAdUnitId: String = "",
    val showRewarded: Boolean = true,
    val rewardedAdUnitId: String = ""
)

class AdsViewModel(
    private val remoteConfigService: RemoteConfigService
) : ViewModel() {

    private val _adsConfig = MutableStateFlow(AdsConfig())
    val adsConfig: StateFlow<AdsConfig> = _adsConfig.asStateFlow()

    init {
        viewModelScope.launch {
            remoteConfigService.fetchAndActivate()
            
            _adsConfig.update {
                it.copy(
                    showBannerBottom = remoteConfigService.getBoolean("banner_bottom"),
                    bannerBottomAdUnitId = remoteConfigService.getString("banner_bottom_param"),
                    showInterstitial = remoteConfigService.getBoolean("banner_intersection"),
                    interstitialAdUnitId = remoteConfigService.getString("banner_intersection_param"),
                    showRewarded = remoteConfigService.getBoolean("banner_intersection_rewards"),
                    rewardedAdUnitId = remoteConfigService.getString("banner_intersection_rewards_param")
                )
            }
        }
    }
}
