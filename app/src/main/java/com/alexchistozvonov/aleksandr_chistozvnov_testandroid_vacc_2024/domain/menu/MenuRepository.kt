package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.menu

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse

interface MenuRepository {
    suspend fun getMenu(id: Int): LoadingResult<List<MenuResponse>>
}