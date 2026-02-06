package com.game.forca.game_forca.di

import android.annotation.SuppressLint
import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@SuppressLint("ServiceCast")
fun moduleAndroid() = module {

    //scope(named("gameScreenScope")) {
        //single<> { FirebaseRemoteConfigsBridge() }
        factory { GameScreenviewModel() }
    //}

}