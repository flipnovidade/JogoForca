package com.game.forca.game_forca.di

import com.game.forca.game_forca.data.IosRegisterUserLocalStore
import com.game.forca.game_forca.data.RegisterUserLocalStore
import org.koin.dsl.module

fun moduleIos() = module {
    single<RegisterUserLocalStore> { IosRegisterUserLocalStore() }
}
package com.game.forca.game_forca.di

import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import com.game.forca.game_forca.ui.viewmodel.InfoScreenViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun moduleIos() = module {

    //scope(named("gameScreenScope")) {
        //single<> { FirebaseRemoteConfigsBridge() }
        factory { GameScreenviewModel() }
        factory { InfoScreenViewModel() }
    //}

}