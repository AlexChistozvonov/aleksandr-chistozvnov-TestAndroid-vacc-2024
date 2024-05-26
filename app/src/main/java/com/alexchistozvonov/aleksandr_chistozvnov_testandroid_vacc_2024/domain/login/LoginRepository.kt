package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.login

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationResponse

interface LoginRepository {
    suspend fun login(login: String, password: String): LoadingResult<RegistrationResponse>
}