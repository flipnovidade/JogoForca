package com.game.forca.game_forca.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.game.forca.game_forca.AppVersion
import com.game.forca.game_forca.data.RankingItem
import com.game.forca.game_forca.ui.viewmodel.InfoScreenViewModel
import org.koin.compose.koinInject
import com.game.forca.game_forca.ui.components.RegisterBackHandler

@Composable
fun InfoScreen(
    navController: NavHostController,
    infoScreenViewModel: InfoScreenViewModel = koinInject<InfoScreenViewModel>()
) {
    val ranking by infoScreenViewModel.ranking.collectAsState()
    val isLoading by infoScreenViewModel.isLoading.collectAsState()
    val errorMessage by infoScreenViewModel.errorMessage.collectAsState()
    val myEmail = infoScreenViewModel.myEmail

    val onBack: () -> Unit = {
        navController.popBackStack()
        println("Back pressed")
    }

    RegisterBackHandler  {
        onBack()
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            TopBar(title = "Informações", onBack = onBack)

            Spacer(Modifier.height(24.dp))

            SectionTitle("Ranking Geral", "📊")
            when {
                isLoading -> RankingStatusCard("Carregando ranking...")
                errorMessage != null -> RankingStatusCard(errorMessage ?: "Erro ao carregar ranking.")
                ranking.isEmpty() -> RankingStatusCard("Nenhum ranking encontrado.")
                else -> RankingCard(ranking, myEmail)
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("Detalhes do App", "ℹ️")
            AppDetailsCard(infoScreenViewModel.appVersion)

            Spacer(Modifier.height(24.dp))

            SectionTitle("Créditos", "💙")
            CreditsCard()

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionTitle(x0: String, x1: String) {
    //TODO("Not yet implemented")
}

@Composable
fun CreditsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF121C3D))
            .padding(16.dp)
    ) {
        CreditItem("DESIGNER PRINCIPAL", "Alex Rivers")
        CreditItem("ILUSTRAÇÕES", "Sarah Chen, Vector Lab")
        CreditItem(
            "AGRADECIMENTOS",
            "A todos os nossos testadores beta que ajudaram a eliminar bugs e melhorar nosso jogo."
        )
        CreditItem(
            "PREMIAÇÃO",
            "No último dia de cada mês, na última hora do dia, identificamos os 5 jogadores com maior pontuação. Em seguida, fazemos uma verificação para garantir que todos os pontos estejam de acordo com as regras. Caso algum jogador não atenda aos critérios, ele será desclassificado e o ranking será atualizado. Após a análise final, entraremos em contato com os vencedores pelo e-mail cadastrado para confirmar os dados e realizar o envio dos prêmios."
        )
    }
}

@Composable
private fun RankingRow(
    item: RankingItem,
    highlight: Boolean
) {
    val bg = when {
        highlight -> Color(0xFF102A66)
        item.position == 1 -> Color(0xFF2A2412)
        item.position == 2 -> Color(0xFF1E222E)
        item.position == 3 -> Color(0xFF2A1E12)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        PositionBadge(item.position)

        Text(
            text = "  " + item.email,
            color = if (highlight) Color(0xFF3B7CFF) else Color.White,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = item.score.toString(),
            color = if (highlight) Color(0xFF3B7CFF) else Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HeaderRow() {
    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        HeaderText("POSIÇÃO", Modifier.width(80.dp))
        HeaderText("EMAIL", Modifier.weight(1f))
        HeaderText("PONTOS", Modifier.width(80.dp), alignEnd = true)
    }
}

@Composable
fun RankingCard(
    ranking: List<RankingItem>,
    myEmail: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF121C3D))
            .padding(12.dp)
    ) {

        HeaderRow()

        ranking.forEach { item ->
            RankingRow(
                item = item,
                highlight = item.email == myEmail
            )
        }
    }
}

@Composable
private fun RankingStatusCard(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF121C3D))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HeaderText(
    text: String,
    modifier: Modifier,
    alignEnd: Boolean = false
) {
    Text(
        text = text,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp,
        color = Color.White.copy(alpha = 0.5f),
        modifier = modifier,
        textAlign = if (alignEnd) TextAlign.End else TextAlign.Start
    )
}

@Composable
private fun CreditItem(
    title: String,
    content: String
) {
    Text(
        text = title,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp,
        color = Color(0xFF3B7CFF)
    )
    Spacer(Modifier.height(6.dp))
    Text(
        text = content,
        color = Color.White,
        lineHeight = 20.sp
    )
    Spacer(Modifier.height(16.dp))
}


@Composable
fun AppDetailsCard(appVersion: AppVersion) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF121C3D))
            .padding(16.dp)
    ) {
        DetailRow(
            "Versão",
            "${appVersion.name} (Build ${appVersion.build})",
            trailing = "ATUALIZADO"
        )
        DetailRow("Desenvolvedor", "Flip Soft.")
        DetailRow("Privacidade", "Outubro 2023")
    }
}

@Composable
private fun DetailRow(
    title: String,
    value: String,
    trailing: String? = null
) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Medium)
            Text(value, color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
        }

        trailing?.let {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E4BFF))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(it, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun PositionBadge(position: Int) {
    val color = when (position) {
        1 -> Color(0xFFFFC107)
        2 -> Color(0xFF9E9E9E)
        3 -> Color(0xFFFF9800)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = if (position <= 3) 0.25f else 0f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${position}º",
            color = if (position <= 3) color else Color.White.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TopBar(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "←",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBack() }
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}