package com.game.forca.game_forca.ad

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMobileAds.GADBannerView
import cocoapods.GoogleMobileAds.GADRequest
import cocoapods.GoogleMobileAds.GADInterstitialAd
import cocoapods.GoogleMobileAds.GADRewardedAd
import cocoapods.GoogleMobileAds.kGADAdSizeBanner
import platform.UIKit.UIApplication

@Composable
actual fun AdMobBanner(modifier: Modifier){
    UIKitView(
        factory = {
            val banner = GADBannerView(adSize = kGADAdSizeBanner)
            banner.adUnitID = "ca-app-pub-3940256099942544/2934735716"
            banner.rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            banner.loadRequest(GADRequest())
            banner
        },
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .height(50.dp),
        update = { },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}

@Composable
actual fun AdMobInterstitial(modifier: Modifier) {
    val (shown, setShown) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (shown) return@LaunchedEffect
        GADInterstitialAd.loadWithAdUnitID(
            adUnitID = "ca-app-pub-3940256099942544/4411468910",
            request = GADRequest(),
            completionHandler = { ad, _ ->
                if (!shown && ad != null) {
                    setShown(true)
                    val root = UIApplication.sharedApplication.keyWindow?.rootViewController
                    if (root != null) {
                        ad.presentFromRootViewController(root)
                    }
                }
            }
        )
    }
}

@Composable
actual fun AdMobRewarded(modifier: Modifier) {
    val (shown, setShown) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (shown) return@LaunchedEffect
        GADRewardedAd.loadWithAdUnitID(
            adUnitID = "ca-app-pub-3940256099942544/1712485313",
            request = GADRequest(),
            completionHandler = { ad, _ ->
                if (!shown && ad != null) {
                    setShown(true)
                    val root = UIApplication.sharedApplication.keyWindow?.rootViewController
                    if (root != null) {
                        ad.presentFromRootViewController(root) { _ ->
                            // reward earned
                        }
                    }
                }
            }
        )
    }
}