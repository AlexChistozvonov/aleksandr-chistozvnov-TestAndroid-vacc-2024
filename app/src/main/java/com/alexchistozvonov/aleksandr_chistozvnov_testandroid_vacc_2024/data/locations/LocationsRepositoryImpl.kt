package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.locations

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.Api
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di.IoDispatcher
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.ErrorMapper
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.runLoading
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.locations.LocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val networkService: Api,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper
) : LocationsRepository {
    override suspend fun getLocations(): LoadingResult<List<LocationsResponse>> =
        withContext(coroutineDispatcher) {
            runLoading(errorMapper) {
                networkService.getLocations()
            }
        }
}
