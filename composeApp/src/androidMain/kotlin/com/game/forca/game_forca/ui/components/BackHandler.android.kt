package com.game.forca.game_forca.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun RegisterBackHandler(onBack: () -> Unit) {
    BackHandler {
        onBack()
    }
}