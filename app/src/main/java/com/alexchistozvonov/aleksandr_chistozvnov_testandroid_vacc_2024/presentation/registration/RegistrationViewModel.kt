package com.example.sevenwindsstudiotest.presentation.registration

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.constants.PreferencesKey
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.registration.RegistrationRepository
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.registration.RegistrationEvent
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.registration.RegistrationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationViewState())
    val uiState = _uiState.asStateFlow()
    private val loading = MutableLiveData<Boolean>()

    private fun emitEvent(event: RegistrationEvent) {
        _uiState.value = _uiState.value.applyEvent(event)
    }

    fun onEmailTextChanged(text: String) {
        emitEvent(RegistrationEvent.EmailChanged(text))
    }

    fun onPasswordTextChanged(text: String, repeatField: Boolean = false) {
        if (repeatField) {
            emitEvent(RegistrationEvent.Password2Changed(text))
        } else {
            emitEvent(RegistrationEvent.PasswordChanged(text))
        }
    }

    fun registration() {
        emitEvent(RegistrationEvent.Loading)

        viewModelScope.launch {
            val result = registrationRepository.registration(
                _uiState.value.emailText,
                _uiState.value.passwordText,
            )
            when (result) {
                is LoadingResult.Error -> {
                    emitEvent(RegistrationEvent.Error(result.exception))
                }

                is LoadingResult.Success -> {
                    emitEvent(RegistrationEvent.Success)
                    loading.postValue(false)
                    sharedPreferences.edit().apply() {
                        putString(PreferencesKey.ACCESS_TOKEN, result.data.token)
                    }.apply()
                }
            }
        }
    }
}