package com.game.forca.game_forca.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.game.forca.game_forca.data.closeApp
import com.game.forca.game_forca.data.requestTrackingAuthorization
import com.game.forca.game_forca.interfaces.GameDialogAction
import com.game.forca.game_forca.interfaces.GameEvent
import com.game.forca.game_forca.ui.components.RegisterBackHandler
import com.game.forca.game_forca.ui.dialog.GameOverDialog
import com.game.forca.game_forca.ui.dialog.VictoryDialog
import com.game.forca.game_forca.ui.viewmodel.GameScreenviewModel
import org.jetbrains.compose.resources.painterResource

import org.koin.compose.koinInject

@Composable
fun GameScreen(
    navController: NavHostController,
    gameScreenviewModel: GameScreenviewModel = koinInject<GameScreenviewModel>()
) {

    val backStackEntries by navController.currentBackStack.collectAsState()

    val state by gameScreenviewModel.state.collectAsState()
    val currentRoute = navController.currentDestination?.route

    val displayWord = state.word.mapIndexed { index, char ->
        if (index in state.revealedIndexes) char else ' '
    }

    val usedLetters = remember(state.revealedIndexes, state.wrongLetters) {
        state.revealedIndexes
            .map { state.word[it] }
            .toSet() + state.wrongLetters
    }

    LaunchedEffect(currentRoute) {
        println("📍 Rota mudou para: $currentRoute")
    }

    LaunchedEffect(Unit) {
        println("Pilha atual:")
        backStackEntries.forEachIndexed { i, entry ->
            println("[$i] ${entry.destination.route}")
        }
    }

    val openDialogMakeLogin by gameScreenviewModel.openDialogMakeLogin.collectAsState()

    LaunchedEffect(openDialogMakeLogin) {
        if (openDialogMakeLogin) {
            navController.navigate("makelogin")
        }
    }

    LaunchedEffect(state.showHintDialog) {
        if (state.showHintDialog) {
            navController.navigate("hint/${state.assayHelp}")
        }
    }

    LaunchedEffect(state.showWinDialog) {
        if (state.showWinDialog) {
            navController.navigate("victory/${state.word}/${state.finalScore}")
        }
    }

    LaunchedEffect(state.showErrorDialog) {
        if (state.showErrorDialog) {
            navController.navigate("gameOver/${state.word}/${gameScreenviewModel.globalScore.value}")
        }
    }

    val resultFlowMakeLoginDialog =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<GameDialogAction?>(
                "make_login_dialog_action",
                null
            )
    LaunchedEffect(resultFlowMakeLoginDialog) {
        resultFlowMakeLoginDialog?.collect { action ->
            when (action) {
                GameDialogAction.RETRY,-> {
                    gameScreenviewModel.onLoginRequired()

                }
                GameDialogAction.NEXT_WORD,
                GameDialogAction.BACK_TO_MENU,
                GameDialogAction.DISMISS,
                null -> {
                    println("null")
                }
            }

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<GameDialogAction>("make_login_dialog_action")
        }
    }


    val resultFlowHintDialog =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<GameDialogAction?>(
                "hint_dialog_action",
                null
            )
    LaunchedEffect(resultFlowHintDialog) {
        resultFlowHintDialog?.collect { action ->
            when (action) {
                GameDialogAction.RETRY,
                GameDialogAction.NEXT_WORD,
                GameDialogAction.BACK_TO_MENU,
                GameDialogAction.DISMISS   -> {
                    println("non exists")
                    gameScreenviewModel.onEvent(GameEvent.CloseHint)
                }
                null -> {
                    println("null")
                }
            }

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<GameDialogAction>("hint_dialog_action")
        }
    }

    val resultFlowVictoryDialog =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<GameDialogAction?>(
                "victory_dialog_action",
                null
            )
    LaunchedEffect(resultFlowVictoryDialog) {
        resultFlowVictoryDialog?.collect { action ->
            when (action) {
                GameDialogAction.DISMISS,
                GameDialogAction.NEXT_WORD,
                GameDialogAction.BACK_TO_MENU,
                GameDialogAction.RETRY -> {
                    println("non exists")
                    gameScreenviewModel.getNextWord()
                }
                null -> {
                    println("null")
                }
            }

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<GameDialogAction>("victory_dialog_action")
        }
    }

    requestTrackingAuthorization()

    val resultFlowGameOverDialog =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<GameDialogAction?>(
                "game_over_dialog_action",
                null
            )

    LaunchedEffect(resultFlowGameOverDialog) {
        resultFlowGameOverDialog?.collect { action ->
            when (action) {
                GameDialogAction.DISMISS,
                GameDialogAction.NEXT_WORD,
                GameDialogAction.BACK_TO_MENU,
                GameDialogAction.RETRY -> {
                    println("RETRY ok")
                    gameScreenviewModel.getNextWord()
                }
                null -> {
                    println("null")
                }
            }

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<GameDialogAction>("game_over_dialog_action")
        }
    }

    val category: String = state.category
    val score: Int = gameScreenviewModel.globalScore.value
    val wrongLetters: Set<Char> = state.wrongLetters

    val onClose: () -> Unit = {
        // 1. Remove qualquer estado/diálogo
        val previousRoute = navController.previousBackStackEntry?.destination?.route

        println("📤 Fechando GameScreen...")
        println("   Rota anterior: $previousRoute")

        // Se veio do splash, volta para ele
        if (previousRoute == "splashScreen") {
            navController.popBackStack()
        }
        // Se não tem rota anterior ou é null
        else if (previousRoute == null) {
            // Vai direto para o splash
            closeApp()
        }
        // Caso padrão
        else {
            navController.popBackStack()
        }

        println("📍 Tentando fechar...")
        println("   Tem anterior? ${navController.previousBackStackEntry != null}")
    }
    val onHint: () -> Unit = {
        gameScreenviewModel.onEvent(GameEvent.OpenHint)
    }
    val goToInfoScreenRanking: () -> Unit = {
        navController.navigate("infoScreen"){
            //restoreState = true
        }
    }

    val goToRegisterScreen: () -> Unit = {
        navController.navigate("registerScreen")
    }

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    LaunchedEffect(Unit) {

    }

    RegisterBackHandler  {
       onClose()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0B1437),
                        Color(0xFF07112E)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            TopBar(category, score, onClose)

            Spacer(Modifier.height(24.dp))

            HangmanCard(onHint)

            Spacer(Modifier.height(32.dp))

            WordSlots(displayWord)

            Spacer(Modifier.height(24.dp))

            WrongLetters(wrongLetters)

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 🔹 Keyboard ocupa 70% da largura
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxHeight()
                        .padding(top = 25.dp)
                ) {
                    Keyboard(
                        disabledLetters = usedLetters,
                        onLetterClick = { letter ->
                            gameScreenviewModel.onEvent(
                                GameEvent.GuessLetter(letter)
                            )
                        }
                    )
                }

                // 🔹 BottomBar ocupa 30% da largura
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        BottomCircleButton(
                            icon = Icons.Default.Info,
                            label = "INFO",
                            onClick = goToInfoScreenRanking
                        )

                        BottomCircleButton(
                            icon = Icons.Default.Person,
                            label = "PERFIL",
                            onClick = goToRegisterScreen
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun TopBar(
    category: String,
    score: Int,
    onClose: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CircleButton("✕", onClose)

            Spacer(Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "CATEGORY",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    letterSpacing = 2.sp
                )
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(Modifier.weight(1f))

            CircleScore(score)
        }
    }


}

@Composable
private fun HangmanCard(onHint: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF0E1B4D))
            .padding(16.dp)
    ) {

        HangmanDrawing(
            modifier = Modifier.align(Alignment.Center)
        )

        HintButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onHint
        )
    }
}

@Composable
fun HangmanDrawing(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(180.dp)
    ) {

        val stroke = Stroke(width = 6f)

        // Forca
        drawLine(
            color = Color(0xFF6D7EDC),
            start = Offset(40f, 20f),
            end = Offset(40f, size.height),
            strokeWidth = 6f
        )

        drawLine(
            color = Color(0xFF6D7EDC),
            start = Offset(40f, 20f),
            end = Offset(120f, 20f),
            strokeWidth = 6f
        )

        // Cabeça
        drawCircle(
            color = Color(0xFFFF4D4D),
            radius = 24f,
            center = Offset(120f, 60f),
            style = stroke
        )

        // Corpo
        drawLine(Color(0xFFFF4D4D), Offset(120f, 84f), Offset(120f, 140f), 6f)
        drawLine(Color(0xFFFF4D4D), Offset(120f, 100f), Offset(90f, 120f), 6f)
        drawLine(Color(0xFFFF4D4D), Offset(120f, 100f), Offset(150f, 120f), 6f)
        drawLine(Color(0xFFFF4D4D), Offset(120f, 140f), Offset(95f, 170f), 6f)
        drawLine(Color(0xFFFF4D4D), Offset(120f, 140f), Offset(145f, 170f), 6f)
    }
}

@Composable
private fun WordSlots(slots: List<Char>) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        slots.forEach { char ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (char == ' ') "" else char.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(4.dp)
                        .background(
                            if (char == ' ') Color(0xFF2E6BFF)
                            else Color.Transparent,
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun WrongLetters(letters: Set<Char>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "LETRAS ERRADAS",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A2340))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letters.joinToString("  "),
                color = Color(0xFFFF6B6B),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PrimaryButtonScreen(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2E6BFF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CircleButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFF1A2A6C))
            .clickable {
                            onClick()
                       },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 18.sp)
    }
}

@Composable
private fun CircleScore(score: Int) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFF1A2A6C)),
        contentAlignment = Alignment.Center
    ) {
        Text(score.toString(), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HintButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF121C3D))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "💡",
                fontSize = 16.sp
            )
            Text(
                text = "HINT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun Keyboard(
    disabledLetters: Set<Char>,
    onLetterClick: (Char) -> Unit
) {
    val letters = ('A'..'Z').toList().chunked(7)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        letters.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { letter ->
                    KeyButton(
                        letter = letter,
                        enabled = letter !in disabledLetters,
                        onClick = { onLetterClick(letter) }
                    )
                }
            }
        }
    }
}

@Composable
fun KeyButton(
    letter: Char,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (enabled) Color(0xFF2E6BFF)
                else Color.Gray
            )
            .clickable(enabled = enabled) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomCircleButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = Color(0xFF0E1430),
                    shape = CircleShape
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BottomBar(
    onInfo: () -> Unit,
    onProfile: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        BottomCircleButton(
            icon = Icons.Default.Info,
            label = "INFO",
            onClick = onInfo
        )

        BottomCircleButton(
            icon = Icons.Default.Person,
            label = "PERFIL",
            onClick = onProfile
        )
    }
}
