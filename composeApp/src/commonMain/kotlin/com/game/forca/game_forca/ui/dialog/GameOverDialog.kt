package com.game.forca.game_forca.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.game.forca.game_forca.resources.correct_word_label
import com.game.forca.game_forca.resources.game_over_icon
import com.game.forca.game_forca.resources.game_over_subtitle
import com.game.forca.game_forca.resources.game_over_title
import com.game.forca.game_forca.resources.main_menu_button
import com.game.forca.game_forca.resources.try_again_button
import com.game.forca.game_forca.ui.screen.PrimaryButton
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameOverDialogRoute(
    navController: NavHostController,
    correctWord: String = "",
    globalScore: Int = 0
) {
    GameOverDialog(
        correctWord = correctWord,
        globalScore = globalScore,
        onRetry = {
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
fun GameOverDialog(
    correctWord: String,
    globalScore: Int,
    onRetry: () -> Unit,
    onBackToMenu: () -> Unit,
    onDismiss: () -> Unit
) {

    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onDismiss()
              },
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF121C3D),
                            Color(0xFF0A1026)
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Drag handle visual
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.25f))
            )

            Spacer(Modifier.height(20.dp))

            // Ícone de derrota
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF4D4D)),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.game_over_icon), fontSize = 42.sp)
            }

            Spacer(Modifier.height(20.dp))

            // Título
            Text(
                text = stringResource(Res.string.game_over_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = stringResource(Res.string.game_over_subtitle),
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(20.dp))

            // Palavra correta
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.06f))
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(Res.string.correct_word_label),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = correctWord.uppercase(),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            // Estatísticas
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                StatBox(
                    title = "PONTUAÇÃO  DO  JOGO",
                    value = globalScore.toString(),
                    valueColor = Color(0xFF3B7CFF)
                )
            }

            Spacer(Modifier.height(28.dp))

            // Botão principal
            PrimaryButton(
                text = stringResource(Res.string.try_again_button),
                onClick = onRetry
            )

            Spacer(Modifier.height(16.dp))

            // Ação secundária
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBackToMenu()
                }
            ) {
                Text("🏠", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.main_menu_button),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun StatBox(
    title: String,
    value: String,
    valueColor: Color
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = Color.White.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

private fun sendResult(
    navController: NavHostController,
    action: GameDialogAction
) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("game_over_dialog_action", action)

    navController.popBackStack()
}
