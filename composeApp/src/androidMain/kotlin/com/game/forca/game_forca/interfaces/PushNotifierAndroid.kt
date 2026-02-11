package com.game.forca.game_forca.interfaces

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import android.content.Context

class PushNotifierAndroid(private val context: Context) : PushNotifier {

    override suspend fun initialize() {
        FirebaseApp.initializeApp(context)
    }

    override suspend fun getToken(callback: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    callback(null)
                } else {
                    callback(task.result)
                }
            }
    }

}
