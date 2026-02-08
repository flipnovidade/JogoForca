package com.game.forca.game_forca.ui.viewmodel

import com.game.forca.game_forca.data.RegisterUserItem
import com.game.forca.game_forca.data.RegisterUserLocalStore
import com.game.forca.game_forca.data.RegisterUserRepository
import com.game.forca.game_forca.ui.screen.RegisterScreenState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

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

class RegisterScreenViewModel(
    private val registerUserRepository: RegisterUserRepository,
    private val localStore: RegisterUserLocalStore
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _validation = MutableStateFlow(RegisterValidation())
    val validation: StateFlow<RegisterValidation> = _validation.asStateFlow()

    private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

    init {
        viewModelScope.launch {
            val user = localStore.getUser()
            if (user != null && isLoggedIn(user)) {
                _uiState.update {
                    it.copy(
                        screenState = RegisterScreenState.Registered,
                        name = user.name,
                        email = user.email,
                        password = user.password,
                        confirmPassword = user.password,
                        showErrors = false
                    )
                }
                updateValidation()
            }
        }
    }

    fun registerUser() {
        val currentValidation = _validation.value
        if (!currentValidation.isRegisterValid) {
            _uiState.update { it.copy(showErrors = true) }
            updateValidation()
            return
        }

        viewModelScope.launch {
            runCatching {
                val localUser = localStore.getUser()
                val requestUser = RegisterUserItem(
                    idFirebase = localUser?.idFirebase.orEmpty(),
                    name = _uiState.value.name,
                    email = _uiState.value.email.trim(),
                    score = localUser?.score ?: 0,
                    password = _uiState.value.password,
                    keyForPush =  "lolocalUser?.keyForPush.orEmpty()"
                )
                val savedId = registerUserRepository.saveUser(requestUser)
                requestUser.copy(idFirebase = savedId)
            }.onSuccess { savedUser ->
                localStore.saveUser(savedUser)
                _uiState.update {
                    it.copy(
                        name = savedUser.name,
                        email = savedUser.email,
                        password = savedUser.password,
                        screenState = RegisterScreenState.Registered,
                        confirmPassword = savedUser.password,
                        showErrors = false
                    )
                }
                updateValidation()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            localStore.clear()
            _uiState.update {
                it.copy(
                    name = "",
                    email = "",
                    password = "",
                    confirmPassword = "",
                    passwordVisible = false,
                    screenState = RegisterScreenState.Register,
                    showErrors = false
                )
            }
            updateValidation()
        }
    }

    fun loginUser() {
        val currentValidation = _validation.value
        if (!currentValidation.isLoginValid) {
            _uiState.update { it.copy(showErrors = true) }
            updateValidation()
            return
        }

        viewModelScope.launch {
            val snapshot = Firebase.database.reference("users").valueEvents.first()
            val matched = snapshot.children.firstOrNull { child ->
                val user = child.value<RegisterUserItem>()
                user.email.trim() == _uiState.value.email.trim() &&
                    user.password == _uiState.value.password
            }

            val found = matched?.value<RegisterUserItem>()
            if (matched == null || found == null) {
                _uiState.update { it.copy(showErrors = true) }
                updateValidation()
                return@launch
            }

            val savedUser = found.copy(
                idFirebase = matched.key ?: "",
                keyForPush = "localStore.getUser()?.keyForPush nova ",
                score = 0
            )

            Firebase.database.reference("users")
                .child(savedUser.idFirebase)
                .setValue(savedUser)

            localStore.saveUser(savedUser)

            localStore.saveGameProgress(0, 0)

            _uiState.update {
                it.copy(
                    name = savedUser.name,
                    email = savedUser.email,
                    password = savedUser.password,
                    screenState = RegisterScreenState.Registered,
                    confirmPassword = savedUser.password,
                    showErrors = false
                )
            }
            updateValidation()
        }
    }

    private fun isLoggedIn(user: RegisterUserItem): Boolean {
        return user.idFirebase.isNotBlank() &&
            user.name.isNotBlank() &&
            user.email.isNotBlank() &&
            user.password.isNotBlank() &&
            user.keyForPush.isNotBlank()
    }

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
        val normalizedEmail = state.email.trim()
        val isNameValid = if (state.screenState == RegisterScreenState.Login) {
            true
        } else {
            state.name.isNotBlank()
        }
        val isEmailValid = emailRegex.matches(normalizedEmail)
        val isEmailTaken = false
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
            isEmailValid && isPasswordValid

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
