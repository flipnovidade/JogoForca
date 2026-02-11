package com.game.forca.game_forca.intefaces

import com.game.forca.game_forca.interfaces.PushNotifier

class PushNotifierIos() : PushNotifier {
    override fun initialize() {}
    override fun getToken(callback: (String?) -> Unit) {}
}