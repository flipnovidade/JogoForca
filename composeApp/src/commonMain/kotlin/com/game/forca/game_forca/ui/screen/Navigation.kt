package com.game.forca.game_forca.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.game.forca.game_forca.ui.dialog.GameOverDialog
import com.game.forca.game_forca.ui.dialog.GameOverDialogRoute
import com.game.forca.game_forca.ui.dialog.HintDialogRoute
import com.game.forca.game_forca.ui.dialog.MakeLoginDialogRoute
import com.game.forca.game_forca.ui.dialog.VictoryDialog
import com.game.forca.game_forca.ui.dialog.VictoryDialogRoute

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController, 
        startDestination = "splashScreen", 
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) } ) {

        composable(route = "splashScreen") {
            SplashRoute(navController)
        }

        composable(route = "gameForcaScreen") {
            GameScreen(navController)
        }

        composable(route = "registerScreen") {
            RegisterScreen(navController)
        }

        composable(route = "infoScreen") {
            InfoScreen(navController)
        }

        dialog("victory/{word}/{points}",
            arguments = listOf(
                navArgument("word") { type = NavType.StringType },
                navArgument("points") { type = NavType.IntType }
            ),
            dialogProperties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false)
        ) { backStackEntry ->

            val word = backStackEntry.savedStateHandle.get<String>("word") ?: ""
            val points = backStackEntry.savedStateHandle.get<Int>("points") ?: 0

            VictoryDialogRoute(navController = navController, word = word, points = points)
        }

        dialog("gameOver/{correctWord}/{globalScore}",
            arguments = listOf(
                navArgument("correctWord") { type = NavType.StringType },
                navArgument("globalScore") { type = NavType.IntType }
            ),
            dialogProperties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false)
        ) { backStackEntry ->

            val correctWord = backStackEntry.savedStateHandle.get<String>("correctWord") ?: ""
            val globalScore = backStackEntry.savedStateHandle.get<Int>("globalScore") ?: 0

            GameOverDialogRoute(navController = navController, correctWord = correctWord, globalScore = globalScore)
        }

        dialog("hint/{assay}",
            arguments = listOf(
                navArgument("assay") { type = NavType.StringType },
            ),
            dialogProperties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false)
        ) { backStackEntry ->

            val assay = backStackEntry.savedStateHandle.get<String>("assay") ?: ""

            HintDialogRoute(navController = navController, assay = assay)
        }

        dialog("makelogin"
        ) { _ ->
            MakeLoginDialogRoute(navController = navController)
        }

    }
}
