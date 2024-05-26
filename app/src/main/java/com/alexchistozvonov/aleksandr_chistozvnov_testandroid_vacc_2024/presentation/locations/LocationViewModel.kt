package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.locations.LocationsRepository
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations.LocationsEvent
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations.LocationsViewState
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationsViewState())
    val uiState = _uiState.asStateFlow()

    private fun emitEvent(event: LocationsEvent) {
        _uiState.value = _uiState.value.applyEvent(event)
    }

    private val _getUserLocation = MutableLiveData<Point>()
    val showSearchResults: LiveData<Point> get() = _getUserLocation

    fun getLocations() {
        viewModelScope.launch {
            when (val result = locationsRepository.getLocations()) {
                is LoadingResult.Error -> {
                    emitEvent(LocationsEvent.Error(result.exception))
                }

                is LoadingResult.Success -> {
                    emitEvent(LocationsEvent.UpdateLocations(result.data))
                }
            }
        }
    }

    fun getUserLocation() {
        emitEvent(LocationsEvent.Loading)
        val locationManager: LocationManager = MapKitFactory.getInstance().createLocationManager()
        locationManager.requestSingleUpdate(object : LocationListener {
            override fun onLocationStatusUpdated(p0: LocationStatus) {
            }

            override fun onLocationUpdated(p0: com.yandex.mapkit.location.Location) {
                viewModelScope.launch(Dispatchers.IO)
                {
                    val lat = p0.position.latitude
                    val lon = p0.position.longitude
                    _getUserLocation.postValue(Point(lat, lon))
                    Log.e( "1111111","$lat ----- $lon")
                }
            }
        })
    }
}