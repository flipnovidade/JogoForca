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

import com.game.forca.game_forca.analytics.AnalyticsService
import com.game.forca.game_forca.analytics.AnalyticsServiceImpl
import com.game.forca.game_forca.ui.viewmodel.GlobalAnalyticsViewModel
import com.game.forca.game_forca.config.RemoteConfigService
import com.game.forca.game_forca.config.RemoteConfigServiceImpl
import com.game.forca.game_forca.ui.viewmodel.AdsViewModel
import com.game.forca.game_forca.crashlytics.CrashlyticsService
import com.game.forca.game_forca.crashlytics.CrashlyticsServiceImpl

fun sharedModules() = module {
    single<FirebaseInterRankingRepository> { FirebaseFirebaseInterRankingRepository() }
    single<FirebaseInterRegisterLoginRepository> { FirebaseFirebaseInterRegisterLoginRepository() }
    single<FirebaseInterRegisterUserRepository> { FirebaseFirebaseInterRegisterUserRepository() }
    
    single<AnalyticsService> { AnalyticsServiceImpl() }
    single<RemoteConfigService> { RemoteConfigServiceImpl() }
    single<CrashlyticsService> { CrashlyticsServiceImpl() }
    factory { GlobalAnalyticsViewModel(get(), get()) }
    factory { AdsViewModel(get()) }

    factory { InfoScreenViewModel(get(), get()) }
    factory { GameScreenviewModel(get(), get(), get(), get()) }
    factory { RegisterScreenViewModel(get(), get(), get(), get(), get()) }
}