package com.game.forca.game_forca.di

import android.annotation.SuppressLint
import com.game.forca.game_forca.data.AndroidRegisterUserLocalStore
import com.game.forca.game_forca.data.RegisterUserLocalStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@SuppressLint("ServiceCast")
fun moduleAndroid() = module {
    single<RegisterUserLocalStore> { AndroidRegisterUserLocalStore(androidContext()) }
}