package com.game.forca.game_forca.di

import com.game.forca.game_forca.data.FirebaseRankingRepository
import com.game.forca.game_forca.data.FirebaseRegisterUserRepository
import com.game.forca.game_forca.data.RankingRepository
import com.game.forca.game_forca.data.RegisterUserLocalStore
import com.game.forca.game_forca.data.RegisterUserRepository
import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import com.game.forca.game_forca.ui.viewmodel.InfoScreenViewModel
import com.game.forca.game_forca.ui.viewmodel.RegisterScreenViewModel
import org.koin.dsl.module

fun sharedModules() = module {
    single<RankingRepository> { FirebaseRankingRepository() }
    single<RegisterUserRepository> { FirebaseRegisterUserRepository() }
    factory { InfoScreenViewModel(get()) }
    factory { GameScreenviewModel() }
    factory { RegisterScreenViewModel(get(), get()) }
}