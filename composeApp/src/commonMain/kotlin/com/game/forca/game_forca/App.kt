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

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val navController = rememberNavController()
    AppNavigation(navController)

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }
}

@Composable
fun ShowAdBanner() {
    AdMobBanner(modifier = Modifier.fillMaxWidth().padding(16.dp))
}