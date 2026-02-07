package com.game.forca.game_forca.data

import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    fun observeRanking(): Flow<List<RankingItem>>
}
