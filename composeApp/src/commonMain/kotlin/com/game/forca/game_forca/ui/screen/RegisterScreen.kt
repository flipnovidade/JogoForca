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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.game.forca.game_forca.resources.Res
import com.game.forca.game_forca.resources.already_have_account
import com.game.forca.game_forca.resources.back_arrow
import com.game.forca.game_forca.resources.confirm_password_label
import com.game.forca.game_forca.resources.confirm_password_placeholder
import com.game.forca.game_forca.resources.email_exists_error
import com.game.forca.game_forca.resources.email_invalid_error
import com.game.forca.game_forca.resources.email_label
import com.game.forca.game_forca.resources.email_placeholder
import com.game.forca.game_forca.resources.full_name_label
import com.game.forca.game_forca.resources.full_name_placeholder
import com.game.forca.game_forca.resources.login_button
import com.game.forca.game_forca.resources.login_link
import com.game.forca.game_forca.resources.logout_button
import com.game.forca.game_forca.resources.name_required_error
import com.game.forca.game_forca.resources.password_hidden_icon
import com.game.forca.game_forca.resources.password_label
import com.game.forca.game_forca.resources.password_placeholder
import com.game.forca.game_forca.resources.password_required_error
import com.game.forca.game_forca.resources.password_visible_icon
import com.game.forca.game_forca.resources.passwords_mismatch_error
import com.game.forca.game_forca.resources.profile_icon
import com.game.forca.game_forca.resources.register_button
import com.game.forca.game_forca.resources.register_subtitle
import com.game.forca.game_forca.resources.register_title
import com.game.forca.game_forca.resources.terms_privacy_text
import com.game.forca.game_forca.ui.components.RegisterBackHandler
import com.game.forca.game_forca.ui.viewmodel.RegisterScreenViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

enum class RegisterScreenState {
    Register,
    Registered,
    Login
}

@Composable
fun RegisterScreen(
    navController: NavHostController,
    screenState: RegisterScreenState = RegisterScreenState.Register,
    registerScreenViewModel: RegisterScreenViewModel = koinInject<RegisterScreenViewModel>()
) {
    val uiState by registerScreenViewModel.uiState.collectAsState()
    val validation by registerScreenViewModel.validation.collectAsState()

    LaunchedEffect(screenState) {
        //registerScreenViewModel.setScreenState(screenState)
    }

    val currentState = uiState.screenState
    val name = uiState.name
    val email = uiState.email
    val password = uiState.password
    val confirmPassword = uiState.confirmPassword
    val passwordVisible = uiState.passwordVisible
    val showErrors = uiState.showErrors

    val nameError = if (showErrors && !validation.isNameValid) {
        stringResource(Res.string.name_required_error)
    } else {
        null
    }
    val emailError = when {
        validation.shouldValidateEmail && !validation.isEmailValid -> stringResource(Res.string.email_invalid_error)
        currentState == RegisterScreenState.Register &&
            validation.shouldValidateEmail &&
            validation.isEmailTaken ->
            stringResource(Res.string.email_exists_error)
        else -> null
    }
    val passwordError = if (validation.shouldValidatePassword && !validation.isPasswordValid) {
        stringResource(Res.string.password_required_error)
    } else {
        null
    }
    val confirmPasswordError =
        if (validation.shouldValidateConfirm && validation.isPasswordMismatch) {
        stringResource(Res.string.passwords_mismatch_error)
    } else {
        null
    }

    val onNameChange: (String) -> Unit = registerScreenViewModel::onNameChange
    val onEmailChange: (String) -> Unit = registerScreenViewModel::onEmailChange
    val onPasswordChange: (String) -> Unit = registerScreenViewModel::onPasswordChange
    val onConfirmPasswordChange: (String) -> Unit = registerScreenViewModel::onConfirmPasswordChange
    val onTogglePasswordVisibility: () -> Unit =  registerScreenViewModel::togglePasswordVisibility
    val onBack: () -> Unit = {
        navController.popBackStack()
        println("onBack")
    }
    val onRegister: () -> Unit = registerScreenViewModel::registerUser
    val onLogin: () -> Unit = {
        println("onLogin")
    }
    val onLogout: () -> Unit = registerScreenViewModel::logout

    RegisterBackHandler  {
        onBack
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
                text = stringResource(Res.string.register_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.register_subtitle),
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(32.dp))

            LabeledInput(
                label = stringResource(Res.string.full_name_label),
                placeholder = stringResource(Res.string.full_name_placeholder),
                value = name,
                onValueChange = onNameChange,
                enabled = currentState != RegisterScreenState.Registered,
                errorMessage = nameError
            )

            Spacer(Modifier.height(20.dp))

            LabeledInput(
                label = stringResource(Res.string.email_label),
                placeholder = stringResource(Res.string.email_placeholder),
                value = email,
                onValueChange = onEmailChange,
                enabled = currentState != RegisterScreenState.Registered,
                errorMessage = emailError
            )

            Spacer(Modifier.height(20.dp))

            if (currentState != RegisterScreenState.Registered) {
                PasswordInput(
                    label = stringResource(Res.string.password_label),
                    placeholder = stringResource(Res.string.password_placeholder),
                    value = password,
                    isVisible = passwordVisible,
                    onValueChange = onPasswordChange,
                    onToggleVisibility = onTogglePasswordVisibility,
                    enabled = true,
                    errorMessage = passwordError
                )
            }

            if (currentState == RegisterScreenState.Register) {
                Spacer(Modifier.height(20.dp))

                PasswordInput(
                    label = stringResource(Res.string.confirm_password_label),
                    placeholder = stringResource(Res.string.confirm_password_placeholder),
                    value = confirmPassword,
                    isVisible = passwordVisible,
                    onValueChange = onConfirmPasswordChange,
                    onToggleVisibility = onTogglePasswordVisibility,
                    enabled = true,
                    errorMessage = confirmPasswordError
                )
            }

            Spacer(Modifier.height(32.dp))

            when (currentState) {
                RegisterScreenState.Register -> {
                    PrimaryButton(
                        text = stringResource(Res.string.register_button),
                        onClick = {
                            registerScreenViewModel.setShowErrors(true)
                            if (validation.isRegisterValid) {
                                onRegister()
                            }
                        },
                        enabled = true
                    )
                }
                RegisterScreenState.Login -> {
                    PrimaryButton(
                        text = stringResource(Res.string.login_button),
                        onClick = {
                            registerScreenViewModel.setShowErrors(true)
                            if (validation.isLoginValid) {
                                onLogin()
                            }
                        },
                        enabled = true
                    )
                }
                RegisterScreenState.Registered -> {
                    PrimaryButton(
                        text = stringResource(Res.string.logout_button),
                        onClick = {
                            registerScreenViewModel.setShowErrors(false)
                            onLogout()
                        },
                        enabled = true
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(Res.string.terms_privacy_text),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            when (currentState) {
                RegisterScreenState.Register -> {
                    Row {
                        Text(
                            text = stringResource(Res.string.already_have_account),
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = stringResource(Res.string.login_link),
                            color = Color(0xFF4A8CFF),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                registerScreenViewModel.setShowErrors(false)
                                registerScreenViewModel.setScreenState(RegisterScreenState.Login)
                            }
                        )
                    }

                    Spacer(Modifier.height(32.dp))
                }
                RegisterScreenState.Login -> {
                    Row {
                        Text(
                            text = stringResource(Res.string.register_button),
                            color = Color(0xFF4A8CFF),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                registerScreenViewModel.setShowErrors(false)
                                registerScreenViewModel.setScreenState(RegisterScreenState.Register)
                            }
                        )
                    }

                    Spacer(Modifier.height(32.dp))
                }
                RegisterScreenState.Registered -> Unit
            }
        }
    }
}

@Composable
fun TopBar(onBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.back_arrow),
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
            text = stringResource(Res.string.profile_icon),
            fontSize = 32.sp
        )
    }
}

@Composable
fun LabeledInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    errorMessage: String? = null
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
                .background(Color.White.copy(alpha = if (enabled) 0.05f else 0.02f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                textStyle = TextStyle(
                    color = Color.White.copy(alpha = if (enabled) 1f else 0.6f),
                    fontSize = 16.sp
                ),
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

        if (!errorMessage.isNullOrBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = errorMessage,
                color = Color(0xFFFF6B6B),
                fontSize = 12.sp
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
    onToggleVisibility: () -> Unit,
    enabled: Boolean = true,
    errorMessage: String? = null
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
                .background(Color.White.copy(alpha = if (enabled) 0.05f else 0.02f))
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
                    enabled = enabled,
                    textStyle = TextStyle(
                        color = Color.White.copy(alpha = if (enabled) 1f else 0.6f),
                        fontSize = 16.sp
                    ),
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
                    text = if (isVisible) {
                        stringResource(Res.string.password_visible_icon)
                    } else {
                        stringResource(Res.string.password_hidden_icon)
                    },
                    modifier = Modifier
                        .clickable(enabled = enabled) { onToggleVisibility() }
                        .padding(start = 8.dp)
                )
            }
        }

        if (!errorMessage.isNullOrBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = errorMessage,
                color = Color(0xFFFF6B6B),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (enabled) Color(0xFF4A8CFF) else Color(0xFF4A8CFF).copy(alpha = 0.5f))
            .clickable(enabled = enabled) { onClick() },
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