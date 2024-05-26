package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.registration

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationResponse

interface RegistrationRepository {

    suspend fun registration(
        login: String,
        password: String
    ): LoadingResult<RegistrationResponse>
}