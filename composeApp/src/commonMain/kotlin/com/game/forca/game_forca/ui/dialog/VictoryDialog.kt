package com.game.forca.game_forca.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.game.forca.game_forca.interfaces.GameDialogAction
import com.game.forca.game_forca.resources.Res
import com.game.forca.game_forca.resources.back_to_menu_button
import com.game.forca.game_forca.resources.next_word_button
import com.game.forca.game_forca.resources.points_accumulated
import com.game.forca.game_forca.resources.star_icon
import com.game.forca.game_forca.resources.victory_icon
import com.game.forca.game_forca.resources.victory_title
import com.game.forca.game_forca.resources.word_was_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun VictoryDialogRoute(
    navController: NavHostController,
    word: String,
    points: Int
) {
    VictoryDialog(
        word = word, // ❌ NÃO USE
        points = points,
        onNextWord = {
            sendResult(navController, GameDialogAction.RETRY)
        },
        onBackToMenu = {
            sendResult(navController, GameDialogAction.RETRY)
        },
        onDismiss = {
            sendResult(navController, GameDialogAction.RETRY)
        }
    )
}

@Composable
fun VictoryDialog(
    word: String,
    points: Int,
    onNextWord: () -> Unit,
    onBackToMenu: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0F1A3A),
                            Color(0xFF0A1026)
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Indicador superior (drag handle visual)
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.3f))
            )

            Spacer(Modifier.height(20.dp))

            // Troféu
            Text(
                text = stringResource(Res.string.victory_icon),
                fontSize = 56.sp
            )

            Spacer(Modifier.height(16.dp))

            // Título
            Text(
                text = stringResource(Res.string.victory_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            // Palavra correta
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0E1B4D))
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.word_was_label),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    color = Color(0xFF3B7CFF)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = word.uppercase(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            // Pontos
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(Res.string.star_icon), fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.points_accumulated, points),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(28.dp))

            // Botão principal
            PrimaryButtonDialog(
                text = stringResource(Res.string.next_word_button),
                onClick = onNextWord
            )

            Spacer(Modifier.height(12.dp))

            // Botão secundário
            SecondaryButton(
                text = stringResource(Res.string.back_to_menu_button),
                onClick = onBackToMenu
            )
        }
    }
}

@Composable
private fun PrimaryButtonDialog(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2E6BFF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun sendResult(
    navController: NavHostController,
    action: GameDialogAction
) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("victory_dialog_action", action)

    navController.popBackStack()
}

