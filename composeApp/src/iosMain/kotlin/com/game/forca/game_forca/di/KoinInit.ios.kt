package com.game.forca.game_forca.di

import org.koin.core.context.startKoin

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class KoinInit {
    fun initKoin() {
        val modules = sharedModules() + moduleIos()
        startKoin {
            modules(modules = modules)
        }
    }
}