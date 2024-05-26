package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.constants.PreferencesKey
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginViewState())
    val uiState = _uiState.asStateFlow()

    private fun emitEvent(event: LoginEvent) {
        _uiState.value = _uiState.value.applyEvent(event)
    }

    fun onEmailTextChanged(text: String) {
        emitEvent(LoginEvent.EmailChanged(text))
    }

    fun onPasswordTextChanged(text: String) {
        emitEvent(LoginEvent.PasswordChanged(text))
    }

    fun login() {
        emitEvent(LoginEvent.Loading)

        viewModelScope.launch {
            val result =
                loginRepository.login(_uiState.value.emailText, _uiState.value.passwordText)
            when (result) {
                is LoadingResult.Error -> {
                    emitEvent(LoginEvent.Error(result.exception))
                }
                is LoadingResult.Success -> {
                    emitEvent(LoginEvent.Success)
                    sharedPreferences.edit().apply {
                        putString(PreferencesKey.ACCESS_TOKEN, result.data.token)
                    }.apply()
                }
            }
        }
    }
}