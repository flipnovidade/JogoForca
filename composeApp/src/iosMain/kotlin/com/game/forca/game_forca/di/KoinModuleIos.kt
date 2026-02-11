package com.game.forca.game_forca.di

import com.game.forca.game_forca.data.IosRegisterUserLocalStore
import com.game.forca.game_forca.data.RegisterUserLocalStore
import com.game.forca.game_forca.intefaces.PushNotifierIos
import com.game.forca.game_forca.interfaces.PushNotifier
import org.koin.dsl.module

fun moduleIos() = module {
    single<PushNotifier> { PushNotifierIos() }
    single<RegisterUserLocalStore> { IosRegisterUserLocalStore() }
}