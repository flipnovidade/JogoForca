package com.game.forca.game_forca.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import org.jetbrains.compose.resources.stringResource
import com.game.forca.game_forca.resources.Res
import com.game.forca.game_forca.resources.make_login_dialog_btn
import com.game.forca.game_forca.resources.make_login_dialog_icon
import com.game.forca.game_forca.resources.make_login_dialog_message
import com.game.forca.game_forca.resources.make_login_dialog_text

@Composable
fun MakeLoginDialogRoute(
    navController: NavHostController,
) {
    MakeLogin(
        onGoToLogin = {
            sendResult(navController, GameDialogAction.RETRY)
        }, navController = navController,
    )
}

@Composable
fun MakeLogin(
    onGoToLogin: () -> Unit,
    navController: NavHostController,
) {

    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onGoToLogin()
                navController.navigate("registerScreen")
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
                text = stringResource(Res.string.make_login_dialog_icon),
                fontSize = 56.sp
            )

            Spacer(Modifier.height(16.dp))

            // Título
            Text(
                text = stringResource(Res.string.make_login_dialog_message),
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
                    text = stringResource(Res.string.make_login_dialog_text),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    color = Color(0xFF3B7CFF)
                )

                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(28.dp))

            // Botão principal
            PrimaryButtonDialog(
                text = stringResource(Res.string.make_login_dialog_btn ),
                onClick = {
                    onGoToLogin()
                    navController.navigate("registerScreen")
                }
            )
            Spacer(Modifier.height(12.dp))
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

private fun sendResult(
    navController: NavHostController,
    action: GameDialogAction
) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("make_login_dialog_action", action)

    navController.popBackStack()
}

