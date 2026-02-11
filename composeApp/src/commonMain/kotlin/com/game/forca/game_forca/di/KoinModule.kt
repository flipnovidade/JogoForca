package com.game.forca.game_forca.di

import com.game.forca.game_forca.data.FirebaseFirebaseInterRankingRepository
import com.game.forca.game_forca.data.FirebaseFirebaseInterRegisterLoginRepository
import com.game.forca.game_forca.data.FirebaseFirebaseInterRegisterUserRepository
import com.game.forca.game_forca.data.FirebaseInterRegisterLoginRepository
import com.game.forca.game_forca.data.FirebaseInterRankingRepository
import com.game.forca.game_forca.data.FirebaseInterRegisterUserRepository
import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import com.game.forca.game_forca.ui.viewmodel.InfoScreenViewModel
import com.game.forca.game_forca.ui.viewmodel.RegisterScreenViewModel
import org.koin.dsl.module

fun sharedModules() = module {
    single<FirebaseInterRankingRepository> { FirebaseFirebaseInterRankingRepository() }
    single<FirebaseInterRegisterLoginRepository> { FirebaseFirebaseInterRegisterLoginRepository() }
    single<FirebaseInterRegisterUserRepository> { FirebaseFirebaseInterRegisterUserRepository() }
    factory { InfoScreenViewModel(get()) }
    factory { GameScreenviewModel(get(), get(), get()) }
    factory { RegisterScreenViewModel(get(), get(), get()) }
}