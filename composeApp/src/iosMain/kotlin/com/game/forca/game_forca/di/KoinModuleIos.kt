package com.game.forca.game_forca.di

import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun moduleIos() = module {

    //scope(named("gameScreenScope")) {
        //single<> { FirebaseRemoteConfigsBridge() }
        factory { GameScreenviewModel() }
    //}

}