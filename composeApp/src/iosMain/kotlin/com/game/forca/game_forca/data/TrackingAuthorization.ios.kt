package com.game.forca.game_forca.data

import platform.AdSupport.ASIdentifierManager
import platform.AppTrackingTransparency.ATTrackingManager
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusAuthorized
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusDenied
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusNotDetermined
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusRestricted
import platform.Foundation.NSLog

actual fun requestTrackingAuthorization() {
    ATTrackingManager.requestTrackingAuthorizationWithCompletionHandler { status ->
        when (status) {
            ATTrackingManagerAuthorizationStatusAuthorized -> {
                // ✅ usuário permitiu tracking
                println("Tracking autorizado")
                NSLog("Tracking autorizado")
                // ✅ 1. pegar IDFA
                val idfa = ASIdentifierManager.sharedManager().advertisingIdentifier.UUIDString
                NSLog("IDFA: $idfa")
            }
            ATTrackingManagerAuthorizationStatusDenied -> {
                println("Tracking negado")
                // usar ads não personalizados
            }
            ATTrackingManagerAuthorizationStatusRestricted -> {
                println("Tracking restrito")
            }
            ATTrackingManagerAuthorizationStatusNotDetermined -> {
                println("Ainda não determinado")
            }
        }
    }
}
