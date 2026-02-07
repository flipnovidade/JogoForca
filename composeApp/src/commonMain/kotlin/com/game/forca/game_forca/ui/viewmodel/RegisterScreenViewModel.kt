package com.game.forca.game_forca.ui.viewmodel

import com.game.forca.game_forca.ui.screen.RegisterScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.asStateFlow

data class RegisterUiState(
    val screenState: RegisterScreenState = RegisterScreenState.Register,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val showErrors: Boolean = false
)

data class RegisterValidation(
    val isNameValid: Boolean = false,
    val isEmailValid: Boolean = false,
    val isEmailTaken: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordMismatch: Boolean = false,
    val shouldValidateEmail: Boolean = false,
    val shouldValidatePassword: Boolean = false,
    val shouldValidateConfirm: Boolean = false,
    val isRegisterValid: Boolean = false,
    val isLoginValid: Boolean = false
)

class RegisterScreenViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _validation = MutableStateFlow(RegisterValidation())
    val validation: StateFlow<RegisterValidation> = _validation.asStateFlow()

    private val existingEmails = setOf(
        "me@email.com",
        "alice@email.com",
        "bob@email.com"
    )
    private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

    fun setScreenState(state: RegisterScreenState) {
        _uiState.update { it.copy(screenState = state, showErrors = false) }
        updateValidation()
    }

    fun setShowErrors(show: Boolean) {
        _uiState.update { it.copy(showErrors = show) }
        updateValidation()
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
        updateValidation()
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
        updateValidation()
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
        updateValidation()
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
        updateValidation()
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    private fun updateValidation() {
        val state = _uiState.value
        val isNameValid = state.name.isNotBlank()
        val isEmailValid = emailRegex.matches(state.email)
        val isEmailTaken = state.email.isNotBlank() && state.email.lowercase() in existingEmails
        val isPasswordValid = state.password.isNotBlank()
        val isPasswordMismatch =
            state.screenState == RegisterScreenState.Register &&
                state.password != state.confirmPassword
        val shouldValidateEmail =
            state.screenState != RegisterScreenState.Registered && state.showErrors
        val shouldValidatePassword =
            state.screenState != RegisterScreenState.Registered && state.showErrors
        val shouldValidateConfirm =
            state.screenState == RegisterScreenState.Register && state.showErrors
        val isRegisterValid =
            isNameValid && isEmailValid && !isEmailTaken && isPasswordValid && !isPasswordMismatch
        val isLoginValid =
            isNameValid && isEmailValid && isPasswordValid

        _validation.value = RegisterValidation(
            isNameValid = isNameValid,
            isEmailValid = isEmailValid,
            isEmailTaken = isEmailTaken,
            isPasswordValid = isPasswordValid,
            isPasswordMismatch = isPasswordMismatch,
            shouldValidateEmail = shouldValidateEmail,
            shouldValidatePassword = shouldValidatePassword,
            shouldValidateConfirm = shouldValidateConfirm,
            isRegisterValid = isRegisterValid,
            isLoginValid = isLoginValid
        )
    }
}
