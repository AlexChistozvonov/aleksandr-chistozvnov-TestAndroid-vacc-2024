package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.login


sealed class LoginEvent {
    object Success : LoginEvent()
    object Loading : LoginEvent()
    object Init : LoginEvent()
    object EmailError : LoginEvent()
    object PasswordError : LoginEvent()
    data class Error(val error: Exception?) : LoginEvent()
    data class EmailChanged(val text: String) : LoginEvent()
    data class PasswordChanged(val text: String) : LoginEvent()
}

data class LoginViewState(
    val error: Exception? = null,
    val emailText: String = "",
    val passwordText: String = "",
    val passwordError: Boolean = false,
    val emailError: Boolean = false,
    val loading: Boolean = false,
    val event: LoginEvent = LoginEvent.Init
) {
    fun applyEvent(event: LoginEvent) = when (event) {
        is LoginEvent.EmailChanged -> copy(
            emailText = event.text,
            emailError = false,
            event = event
        )
        LoginEvent.Loading -> copy(loading = true, event = event)
        is LoginEvent.PasswordChanged -> copy(passwordText = event.text, event = event)
        LoginEvent.EmailError -> copy(emailError = true, event = event)
        LoginEvent.PasswordError -> copy(passwordError = true, event = event)
        is LoginEvent.Error -> copy(error = event.error, event = event)
        LoginEvent.Success -> copy(loading = false, event = event)
        LoginEvent.Init -> copy(event = event)
    }
}