package com.game.forca.game_forca.data

data class GameState(
    val word: String,
    val revealedIndexes: Set<Int> = emptySet(),
    val wrongLetters: Set<Char> = emptySet(),
    val attemptsLeft: Int,
    val maxErrors: Int = 3,
    val usedHelp: Boolean = false,
    val showWinDialog: Boolean = false,
    val showErrorDialog: Boolean = false,
    val showHintDialog: Boolean = false,
    val finalScore: Int = 0, // score de acertar a palavra
    val category: String = "",
    val assayHelp: String = "",
    val hintUsed: Boolean = false,
){
    val errors: Int get() = wrongLetters.size
}