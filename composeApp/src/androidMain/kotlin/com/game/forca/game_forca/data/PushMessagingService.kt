package com.game.forca.game_forca.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.game.forca.game_forca.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: "Notification"
        val body = message.notification?.body ?: ""
        showNotification(title, body)
    }

    override fun onNewToken(token: String) {
        Log.d("TAG", "Refreshed token: $token")
        CoroutineScope(Dispatchers.IO).launch {
            val store = AndroidRegisterUserLocalStore(applicationContext)
            val user = store.getUser()
            if (user != null) {
                store.saveUser(user.copy(keyForPush = token))
            }else{
                store.saveUser(RegisterUserItem(keyForPush = token))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, body: String) {
        val channelId = "default_channel"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (manager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(
                channelId,
                "Default",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }
}
