package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.login

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.Api
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di.IoDispatcher
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.ErrorMapper
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.runLoading
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationRequest
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.login.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val networkService: Api,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper
) : LoginRepository {
    override suspend fun login(login: String, password: String) = withContext(coroutineDispatcher) {
        runLoading(errorMapper) {
            networkService.login(RegistrationRequest(login = login, password = password))
        }
    }
}
