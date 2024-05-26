package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.menu

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse

sealed class MenuEvent {
    data class Success(val menu: List<MenuResponse>?) : MenuEvent()
    object Loading : MenuEvent()
    data class Error(val error: Exception?) : MenuEvent()
    object Init : MenuEvent()
}

data class MenuViewState(
    val error: Exception? = null,
    val loading: Boolean = false,
    val menu: List<MenuResponse>? = null,
    val event: MenuEvent = MenuEvent.Init
) {
    fun applyEvent(event: MenuEvent) = when (event) {
        is MenuEvent.Error -> copy(error = event.error, event = event)
        is MenuEvent.Success -> copy(menu = event.menu, loading = false, event = event)
        MenuEvent.Init -> copy(event = event)
        MenuEvent.Loading -> copy(loading = true, event = event)
    }
}