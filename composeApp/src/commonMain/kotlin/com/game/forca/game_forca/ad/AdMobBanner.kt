package com.game.forca.game_forca.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AdMobBanner(modifier: Modifier = Modifier)

@Composable
expect fun AdMobRewarded(modifier: Modifier = Modifier)

@Composable
expect fun AdMobInterstitial(modifier: Modifier = Modifier)