package com.example.sevenwindsstudiotest.presentation.map

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse

sealed class MapEvent {
    object Success : MapEvent()
    object Loading : MapEvent()
    data class Error(val error: Exception?) : MapEvent()
    object Init : MapEvent()
    data class UpdateLocations(val locations: List<LocationsResponse>?) : MapEvent()
}

data class MapViewState(
    val error: Exception? = null,
    val loading: Boolean = false,
    val loader: Boolean = false,
    val locations: List<LocationsResponse>? = null,
    val event: MapEvent = MapEvent.Init
) {
    fun applyEvent(event: MapEvent) = when (event) {
        is MapEvent.Error -> copy(error = event.error, event = event)
        MapEvent.Success -> copy(loader = true, event = event)
        MapEvent.Init -> copy(event = event)
        MapEvent.Loading -> copy(loading = true, event = event)
        is MapEvent.UpdateLocations -> copy(
            locations = event.locations,
            loading = false,
            event = event
        )
    }
}