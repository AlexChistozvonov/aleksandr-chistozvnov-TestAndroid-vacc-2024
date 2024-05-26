package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.LoadingResult
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.menu.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuViewState())
    val uiState = _uiState.asStateFlow()

    private fun emitEvent(event: MenuEvent) {
        _uiState.value = _uiState.value.applyEvent(event)
    }

    fun getMenu(id: Int) {
        emitEvent(MenuEvent.Loading)
        viewModelScope.launch {
            when (val result = menuRepository.getMenu(id = id)) {
                is LoadingResult.Error -> emitEvent(MenuEvent.Error(result.exception))
                is LoadingResult.Success -> emitEvent(MenuEvent.Success(result.data))
            }
        }
    }
}
