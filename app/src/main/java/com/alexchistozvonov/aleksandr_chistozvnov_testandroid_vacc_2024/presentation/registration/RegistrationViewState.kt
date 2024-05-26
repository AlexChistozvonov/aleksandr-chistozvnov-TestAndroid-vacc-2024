package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.registration

sealed class RegistrationEvent {
    object Init : RegistrationEvent()
    object Success : RegistrationEvent()
    object Loading : RegistrationEvent()
    object PasswordError : RegistrationEvent()
    object EmailError : RegistrationEvent()
    data class Error(val error: Exception?) : RegistrationEvent()
    data class EmailChanged(val text: String) : RegistrationEvent()
    data class PasswordChanged(val text: String) : RegistrationEvent()
    data class Password2Changed(val text: String) : RegistrationEvent()
}

data class RegistrationViewState(
    val error: Exception? = null,
    val emailText: String = "",
    val passwordText: String = "",
    val password2Text: String = "",
    val passwordError: Boolean = false,
    val emailError: Boolean = false,
    val loading: Boolean = false,
    val event: RegistrationEvent = RegistrationEvent.Init
) {
    fun applyEvent(event: RegistrationEvent) = when (event) {
        RegistrationEvent.EmailError -> copy(emailError = true, event = event)
        is RegistrationEvent.Error -> copy(error = event.error, event = event)
        RegistrationEvent.Init -> copy(event = event)
        RegistrationEvent.Loading -> copy(loading = true, event = event)
        RegistrationEvent.PasswordError -> copy(passwordError = true, event = event)
        RegistrationEvent.Success -> copy(loading = false, event = event)
        is RegistrationEvent.EmailChanged -> copy(
            emailText = event.text,
            emailError = false,
            event = event
        )
        is RegistrationEvent.Password2Changed -> copy(password2Text = event.text, event = event)
        is RegistrationEvent.PasswordChanged -> copy(passwordText = event.text, event = event)
    }
}