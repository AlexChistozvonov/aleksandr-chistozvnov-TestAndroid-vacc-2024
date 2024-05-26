package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse

sealed class LocationsEvent {
    object Success : LocationsEvent()
    object Loading : LocationsEvent()
    data class Error(val error: Exception?) : LocationsEvent()
    object Init : LocationsEvent()
    data class UpdateLocations(val locations: List<LocationsResponse>?) : LocationsEvent()
}

data class LocationsViewState(
    val error: Exception? = null,
    val loading: Boolean = false,
    val loader: Boolean = false,
    val locations: List<LocationsResponse>? = null,
    val event: LocationsEvent = LocationsEvent.Init
) {
    fun applyEvent(event: LocationsEvent) = when (event) {
        is LocationsEvent.Error -> copy(error = event.error, event = event)
        LocationsEvent.Success -> copy(loader = true, event = event)
        LocationsEvent.Init -> copy(event = event)
        LocationsEvent.Loading -> copy(loading = true, event = event)
        is LocationsEvent.UpdateLocations -> copy(
            locations = event.locations,
            loading = false,
            event = event
        )
    }
}