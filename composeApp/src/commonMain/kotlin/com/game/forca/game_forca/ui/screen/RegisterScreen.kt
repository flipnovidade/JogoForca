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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(navController: NavHostController) {

    var name: String = ""
    var email: String = ""
    var password: String = ""
    var confirmPassword: String = ""
    var passwordVisible: Boolean = false

    var onNameChange: (String) -> Unit = { value ->
        println("Senha confirmada: $value")
    }
    var onEmailChange: (String) -> Unit = { value ->
        println("Senha confirmada: $value")
    }
    var onPasswordChange: (String) -> Unit = { value ->
        println("Senha confirmada: $value")
    }
    var onConfirmPasswordChange: (String) -> Unit = { value ->
        println("Senha confirmada: $value")
    }
    var onTogglePasswordVisibility: () -> Unit= {
        println("onTogglePasswordVisibility")
    }
    var onBack: () -> Unit = {
        println("onBack")
    }
    var onRegister: () -> Unit = {
        println("onRegister")
    }
    var onLogin: () -> Unit = {
        println("onLogin")
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
            .padding(horizontal = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))

            TopBar(onBack)

            Spacer(Modifier.height(32.dp))

            ProfileIcon()

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Novo Jogador",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Crie sua conta no Hangman",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(32.dp))

            LabeledInput(
                label = "NOME COMPLETO",
                placeholder = "Como quer ser chamado?",
                value = name,
                onValueChange = onNameChange
            )

            Spacer(Modifier.height(20.dp))

            LabeledInput(
                label = "E-MAIL",
                placeholder = "seu@email.com",
                value = email,
                onValueChange = onEmailChange
            )

            Spacer(Modifier.height(20.dp))

            PasswordInput(
                label = "SENHA",
                placeholder = "Crie uma senha forte",
                value = password,
                isVisible = passwordVisible,
                onValueChange = onPasswordChange,
                onToggleVisibility = onTogglePasswordVisibility
            )

            Spacer(Modifier.height(20.dp))

            PasswordInput(
                label = "CONFIRMAR SENHA",
                placeholder = "Repetir Senha",
                value = confirmPassword,
                isVisible = passwordVisible,
                onValueChange = onConfirmPasswordChange,
                onToggleVisibility = onTogglePasswordVisibility
            )

            Spacer(Modifier.height(32.dp))

            PrimaryButton(
                text = "Cadastrar  →",
                onClick = onRegister
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Ao se cadastrar, você concorda com nossos\nTermos de Uso e Privacidade.",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Row {
                Text(
                    text = "Já possui uma conta? ",
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = "Entrar",
                    color = Color(0xFF4A8CFF),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onLogin() }
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun TopBar(onBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "←",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.clickable { onBack() }
        )
    }
}

@Composable
fun ProfileIcon() {
    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFF12255E)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "👤➕",
            fontSize = 32.sp
        )
    }
}

@Composable
fun LabeledInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = Color.White.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                decorationBox = { inner ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.White.copy(alpha = 0.4f)
                        )
                    }
                    inner()
                }
            )
        }
    }
}

@Composable
fun PasswordInput(
    label: String,
    placeholder: String,
    value: String,
    isVisible: Boolean,
    onValueChange: (String) -> Unit,
    onToggleVisibility: () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = Color.White.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                    visualTransformation =
                        if (isVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    decorationBox = { inner ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.White.copy(alpha = 0.4f)
                            )
                        }
                        inner()
                    }
                )

                Text(
                    text = if (isVisible) "🙈" else "👁️",
                    modifier = Modifier
                        .clickable { onToggleVisibility() }
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF4A8CFF))
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