package com.game.forca.game_forca.di

import com.game.forca.game_forca.data.FirebaseRankingRepository
import com.game.forca.game_forca.data.RankingRepository
import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import com.game.forca.game_forca.ui.viewmodel.InfoScreenViewModel
import org.koin.dsl.module

fun sharedModules() = module {
    single<RankingRepository> { FirebaseRankingRepository() }
    factory { InfoScreenViewModel(get()) }
    factory { GameScreenviewModel() }
}