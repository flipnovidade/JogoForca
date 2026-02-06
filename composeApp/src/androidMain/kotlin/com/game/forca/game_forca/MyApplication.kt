package com.game.forca.game_forca

import android.app.Application
import android.content.Context
import com.game.forca.game_forca.di.KoinInit

class MyApplication: Application() {

    companion object {
        @JvmStatic
        private var instance: MyApplication? = null

        @JvmStatic
        public final fun getContext(): Context? {
            return instance
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        KoinInit(androidContext = this@MyApplication)
    }

}