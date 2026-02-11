package com.game.forca.game_forca.data

import kotlinx.coroutines.flow.Flow

interface FirebaseInterRankingRepository {
    fun observeRanking(): Flow<List<RankingItem>>
}
