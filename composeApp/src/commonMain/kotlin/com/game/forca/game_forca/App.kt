package com.game.forca.game_forca

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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