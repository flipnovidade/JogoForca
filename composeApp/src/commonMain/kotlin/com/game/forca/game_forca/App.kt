package com.game.forca.game_forca

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.game.forca.game_forca.ad.AdMobBanner
import com.game.forca.game_forca.ui.screen.AppNavigation

import com.game.forca.game_forca.ui.viewmodel.GlobalAnalyticsViewModel
import com.game.forca.game_forca.ui.viewmodel.AdsViewModel
import org.koin.compose.koinInject

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val navController = rememberNavController()
    val globalAnalyticsViewModel: GlobalAnalyticsViewModel = koinInject()
    AppNavigation(navController, globalAnalyticsViewModel)

    LaunchedEffect(navController) {
        globalAnalyticsViewModel.logAppStart()
        onNavHostReady(navController)
    }
}

@Composable
fun ShowAdBanner() {
    val adsViewModel: AdsViewModel = koinInject()
    val adsConfig by adsViewModel.adsConfig.collectAsState()

    if (adsConfig.showBannerBottom) {
        AdMobBanner(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            adUnitId = adsConfig.bannerBottomAdUnitId
        )
    }
}