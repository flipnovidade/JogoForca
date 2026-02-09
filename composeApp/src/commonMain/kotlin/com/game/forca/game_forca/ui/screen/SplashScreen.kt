package com.game.forca.game_forca.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.game.forca.game_forca.theme.DarkBlueBottom
import com.game.forca.game_forca.theme.DarkBlueMid
import com.game.forca.game_forca.theme.DarkBlueTop
import com.game.forca.game_forca.theme.PrimaryBlue
import com.game.forca.game_forca.theme.PrimaryBlueDark
import kotlinx.coroutines.delay


@Composable
fun SplashRoute(
    navController: NavHostController
) {
    var progress by remember { mutableIntStateOf(0) }
    var navigationTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Animação suave do progresso
        for (i in 1..10) {
            delay(200) // Total de 2 segundos
            progress = i * 10

            // Quando chegar em 40%, marca para navegar
            if (i == 4 && !navigationTriggered) {
                navigationTriggered = true
            }
        }

        // Garante navegação mesmo se a animação terminar
        if (!navigationTriggered) {
            navigationTriggered = true
        }
    }

    // Controle separado para navegação
    LaunchedEffect(navigationTriggered) {
        if (navigationTriggered) {
            // Delay extra para garantir que a UI mostre 100%
            delay(100)

            // Navegação com flags para evitar duplicação
            navController.navigate("gameForcaScreen") {
                popUpTo("splashScreen") {
                    inclusive = true
                }
                popUpTo(0)
                launchSingleTop = true

                // Restaura estado se já estiver na tela
                restoreState = true
            }

            // Impede novas navegações
            navigationTriggered = false
        }
    }

    SplashScreen(progress = progress)
}

@Composable
fun SplashScreen(
    progress: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        DarkBlueTop,
                        DarkBlueMid,
                        DarkBlueBottom
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // Ícone (shape puro → multiplataforma)
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                PrimaryBlue,
                                PrimaryBlueDark
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder multiplataforma
                Text(
                    text = "🔍",
                    fontSize = 36.sp
                )
            }

            Spacer(Modifier.height(32.dp))

            // Título
            Row {
                Text(
                    text = "Hangman ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Pro",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "MASTER THE MYSTERY",
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        // Rodapé
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "INITIALIZING GAME ENGINE",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress.toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                                .width(220.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(2.dp)),
                color = PrimaryBlue,
                trackColor = Color.White.copy(alpha = 0.15f),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            Spacer(Modifier.height(15.dp))

        }

    }
}