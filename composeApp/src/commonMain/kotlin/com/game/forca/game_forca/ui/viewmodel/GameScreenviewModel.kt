package com.game.forca.game_forca.ui.viewmodel

import com.game.forca.game_forca.data.GameState
import com.game.forca.game_forca.data.WordItem
import com.game.forca.game_forca.data.getSystemLocale
import com.game.forca.game_forca.interfaces.GameEvent
import com.game.forca.game_forca.resources.Res
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.Int
import kotlin.String

class GameScreenviewModel: BaseViewModel() {

    private var _globalScore = MutableStateFlow(100)
    val globalScore : StateFlow<Int> = _globalScore

    private var _positionStartedWord = MutableStateFlow(0)
    val positionStartedWord : StateFlow<Int> = _positionStartedWord

    private val _state = MutableStateFlow<GameState>(
        GameState(
                    word = "",
                    attemptsLeft = 0,
                    category = "",
                    assayHelp = "")
                )
    val state: StateFlow<GameState> = _state

    val _myWordItem = MutableStateFlow(
            WordItem(
                        id = 0,
                        category = "",
                        word = "",
                        assay = "")
                    )
    val myWordItem: StateFlow<WordItem> = _myWordItem

    private val _listWordItem = MutableStateFlow<List<WordItem>>(emptyList())
    val listWordItem: StateFlow<List<WordItem>> = _listWordItem.asStateFlow()

    private val _numberFileWords = MutableStateFlow<Int>(0)
    val numberFileWords: StateFlow<Int> = _numberFileWords

    init {
        viewModelScope.launch {
            val myNewList = loadWords()
            _listWordItem.update { list ->
                myNewList
            }

            val wordItem = myNewList[positionStartedWord.value]
            _myWordItem.update {
                it.copy(
                    id = wordItem.id,
                    category = wordItem.category,
                    word = wordItem.word,
                    assay = wordItem.assay
                )
            }

            _state.update {
                it.copy(
                    word = wordItem.word.uppercase(),
                    attemptsLeft = wordItem.word.length,
                    category = wordItem.category.uppercase(),
                    assayHelp = wordItem.assay
                )
            }
        }
    }

    suspend fun getNextWord() {

        val newWrongLetters = state.value.wrongLetters

        if (newWrongLetters.size < state.value.maxErrors) {
            val basePoints = 5
            val errorPenalty = state.value.wrongLetters.size * 1
            val hintPenalty = if (state.value.hintUsed) 2 else 0

            val finalScore = (basePoints - errorPenalty - hintPenalty)
                .coerceAtLeast(0)

            _globalScore.update {
                it + finalScore
            }
        }

        if( (_positionStartedWord.value + 1) == 100 ){
            _numberFileWords.update {
                it + 1
            }
            _positionStartedWord.update {
                0
            }
            loadWords()
        }

        val nextIndex = (_positionStartedWord.value + 1) % _listWordItem.value.size

        _positionStartedWord.value = nextIndex

        val wordItem = _listWordItem.value[nextIndex]

        _myWordItem.value = wordItem

        _state.update {
            it.copy(
                word = wordItem.word.uppercase(),
                attemptsLeft = wordItem.word.length,
                category = wordItem.category.uppercase(),
                assayHelp = wordItem.assay,
                hintUsed = false,
                maxErrors = 3,
                usedHelp = false,
                finalScore = 0,
                revealedIndexes = emptySet(),
                wrongLetters = emptySet(),
                showErrorDialog = false,
                showWinDialog = false
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun readWordsJson(): String {
        val language = getSystemLocale()
        val formattedLocale = language.replace("_", "-").lowercase()
        val fileName = when (formattedLocale) {
            "pt-br", "pt-pt" -> "files/words-pt-br-${numberFileWords.value}.json"
            "en", "en-us", "en-ie", "en-au", "en-gb", "en-ca" -> "files/words-en-${numberFileWords.value}.json"
            else -> "files/words-${numberFileWords.value}.json" // fallback
        }

        return try {
            Res.readBytes(fileName).decodeToString()
        } catch (e: Exception) {
            _numberFileWords.update {
                0
            }
            _positionStartedWord.update {
                0
            }
            Res.readBytes("files/words-en-${numberFileWords.value}.json").decodeToString()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun loadWords(): List<WordItem> {
        val json = readWordsJson()

        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.GuessLetter -> onGuess(event.letter.uppercaseChar())

            is GameEvent.OpenHint -> openHint()

            is GameEvent.CloseHint -> {
                _state.update {
                    it.copy(showHintDialog = false)
                }
            }
        }
    }

    private fun openHint() {
        _state.update { state ->
            state.copy(
                showHintDialog = true,
                hintUsed = true // 👈 marca como usado (UMA VEZ)
            )
        }
    }

    private fun onWordCompleted() {
        _state.update { state ->

            val basePoints = 5
            val errorPenalty = state.wrongLetters.size * 1
            val hintPenalty = if (state.hintUsed) 2 else 0

            val finalScore = (basePoints - errorPenalty - hintPenalty)
                .coerceAtLeast(0)

            state.copy(
                finalScore = finalScore,
                showWinDialog = true
            )
        }
    }

    private fun onGuess(letter: Char) {
        _state.update { state ->

            if (letter in state.word &&
                state.revealedIndexes.none { index -> state.word[index] == letter }
            ) {

                val newIndexes = state.word
                    .mapIndexedNotNull { index, c ->
                        if (c == letter) index else null
                    }
                    .toSet()

                val updatedIndexes = state.revealedIndexes + newIndexes
                val completed = updatedIndexes.size == state.word.length

                if (completed) {
                    onWordCompleted()
                }

                state.copy(
                    revealedIndexes = updatedIndexes
                )

            } else {

                val newWrongLetters = state.wrongLetters + letter

                if (newWrongLetters.size >= state.maxErrors) {
                    onGameOver()
                }

                state.copy(
                    wrongLetters = newWrongLetters
                )
            }
        }
    }

    private fun onGameOver() {
        _state.update { state ->
            state.copy(
                showErrorDialog = true
            )
        }
    }

}

