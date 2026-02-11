package com.game.forca.game_forca.data

import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

actual fun requestNotificationPermission() {
    UNUserNotificationCenter.currentNotificationCenter()
        .requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or
                UNAuthorizationOptionBadge or
                UNAuthorizationOptionSound,
            completionHandler = { _, _ -> }
        )
}
