package com.game.forca.game_forca.data

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

actual fun requestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        return
    }

    val activity = CurrentActivityHolder.activity ?: return
    val permission = Manifest.permission.POST_NOTIFICATIONS
    val granted = ContextCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_GRANTED
    if (!granted) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 1001)
    }
}
