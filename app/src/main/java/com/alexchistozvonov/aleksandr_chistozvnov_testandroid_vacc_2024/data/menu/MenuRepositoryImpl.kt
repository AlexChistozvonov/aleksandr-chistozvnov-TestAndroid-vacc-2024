package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.menu

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.Api
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.ErrorMapper
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.runLoading
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di.IoDispatcher
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.menu.MenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val networkService: Api,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper
) : MenuRepository {

    override suspend fun getMenu(id: Int): LoadingResult<List<MenuResponse>> =
        withContext(coroutineDispatcher) {
            runLoading(errorMapper) {
                networkService.getMenu(id = id)
            }
        }
}
