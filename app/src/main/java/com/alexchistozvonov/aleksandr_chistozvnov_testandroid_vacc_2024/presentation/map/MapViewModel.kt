package com.example.sevenwindsstudiotest.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.locations.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapViewState())
    val uiState = _uiState.asStateFlow()

    private fun emitEvent(event: MapEvent) {
        _uiState.value = _uiState.value.applyEvent(event)
    }

    fun getLocations() {
        viewModelScope.launch {
            when (val result = locationsRepository.getLocations()) {
                is LoadingResult.Error -> {
                    emitEvent(MapEvent.Error(result.exception))
                }

                is LoadingResult.Success -> {
                    emitEvent(MapEvent.UpdateLocations(result.data))
                }
            }
        }
    }
}