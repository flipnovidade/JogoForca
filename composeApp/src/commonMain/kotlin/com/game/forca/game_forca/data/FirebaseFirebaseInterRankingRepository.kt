package com.game.forca.game_forca.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseFirebaseInterRankingRepository : FirebaseInterRankingRepository {
    private val rankingReference = Firebase.database.reference("users")

    override fun observeRanking(): Flow<List<RankingItem>> {
        return rankingReference.valueEvents.map { snapshot ->
            snapshot.children.mapIndexed { index, child ->
                val item = child.value<RankingItem>()
                val position = if (item.position > 0) item.position else index + 1
                item.copy(
                    position = position,
                    keyPush = item.keyPush.ifBlank { (child.key ?: "") }
                )
            }
                .sortedByDescending { it.score }
                .take(5)
                .mapIndexed { rankIndex, item ->
                    item.copy(position = rankIndex + 1)
                }
        }
    }
}
