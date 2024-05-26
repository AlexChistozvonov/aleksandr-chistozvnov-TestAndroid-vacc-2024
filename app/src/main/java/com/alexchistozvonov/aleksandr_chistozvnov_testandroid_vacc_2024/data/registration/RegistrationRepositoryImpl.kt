package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.registration

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.Api
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.ErrorMapper
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.runLoading
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationRequest
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di.IoDispatcher
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.registration.RegistrationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val networkService: Api,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper
) : RegistrationRepository {
    override suspend fun registration(
        login: String,
        password: String,
    ): LoadingResult<RegistrationResponse> = withContext(coroutineDispatcher) {
        runLoading(errorMapper) {
            networkService.registration(RegistrationRequest(login = login, password = password))
        }
    }
}
