package com.game.forca.game_forca.ad

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.game.forca.game_forca.data.CurrentActivityHolder
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

@Composable
actual fun AdMobBanner(modifier: Modifier) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
    }
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-5254546742885775/3187212447"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
actual fun AdMobInterstitial(modifier: Modifier) {
    val context = LocalContext.current
    val (shown, setShown) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
        if (shown) return@LaunchedEffect

        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-5254546742885775/7121388912",
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    val activity = CurrentActivityHolder.activity ?: return
                    if (!shown) {
                        setShown(true)
                        ad.show(activity)
                    }
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    // No-op
                }
            }
        )
    }
}

@Composable
actual fun AdMobRewarded(modifier: Modifier) {
    val context = LocalContext.current
    val (shown, setShown) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
        if (shown) return@LaunchedEffect

        val request = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            "ca-app-pub-5254546742885775/7593361313",
            request,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    val activity = CurrentActivityHolder.activity ?: return
                    if (!shown) {
                        setShown(true)
                        ad.show(activity) { /* reward earned */ }
                    }
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    // No-op
                }
            }
        )
    }
}