package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.locations

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse

interface LocationsRepository {
    suspend fun getLocations(): LoadingResult<List<LocationsResponse>>
}