package com.game.forca.game_forca

import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

lateinit var IOSBanner: () -> UIViewController

fun generateIOSBanner(): UIViewController {
    return IOSBanner()
}

fun MainViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    App()
}