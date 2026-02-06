package com.game.forca.game_forca.interfaces

sealed interface GameEvent {
    data object OpenHint : GameEvent
    data object CloseHint : GameEvent
    data class GuessLetter(val letter: Char) : GameEvent
    //object UseHelp : GameEvent
}