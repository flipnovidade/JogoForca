package com.game.forca.game_forca.ui.viewmodel

import com.game.forca.game_forca.AppVersion
import com.game.forca.game_forca.data.RankingItem
import com.game.forca.game_forca.data.RankingRepository
import com.game.forca.game_forca.getAppVersion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class InfoScreenViewModel(
    private val rankingRepository: RankingRepository
) : BaseViewModel() {
    private val _ranking = MutableStateFlow<List<RankingItem>>(emptyList())
    val ranking: StateFlow<List<RankingItem>> = _ranking

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    val myEmail: String = "me@email.com"
    val appVersion: AppVersion = getAppVersion()

    init {
        viewModelScope.launch {
            rankingRepository.observeRanking()
                .onStart {
                    _isLoading.value = true
                    _errorMessage.value = null
                }
                .catch { throwable ->
                    _errorMessage.value = throwable.message ?: "Erro ao carregar ranking"
                    _isLoading.value = false
                }
                .collect { items ->
                    _ranking.value = items
                    _isLoading.value = false
                }
        }
    }
}
