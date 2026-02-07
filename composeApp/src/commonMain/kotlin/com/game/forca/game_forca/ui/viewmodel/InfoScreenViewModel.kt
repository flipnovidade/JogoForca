package com.game.forca.game_forca.ui.viewmodel

import com.game.forca.game_forca.AppVersion
import com.game.forca.game_forca.data.RankingItem
import com.game.forca.game_forca.getAppVersion

class InfoScreenViewModel : BaseViewModel() {
    val ranking: List<RankingItem> = listOf(
        RankingItem(
            position = 1,
            name = "Alice",
            score = 1500,
            email = "alice@email.com"
        ),
        RankingItem(
            position = 2,
            name = "Bob",
            score = 1200,
            email = "bob@email.com"
        ),
        RankingItem(
            position = 3,
            name = "You",
            score = 900,
            email = "me@email.com"
        )
    )

    val myEmail: String = "me@email.com"

    val appVersion: AppVersion = getAppVersion()
}
