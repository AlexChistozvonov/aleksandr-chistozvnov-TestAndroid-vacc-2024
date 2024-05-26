package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.navigateSafe
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.onClick
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.LocationsFragmentBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LocationsFragment : Fragment(R.layout.locations_fragment) {
    private val binding by viewBinding(LocationsFragmentBinding::bind)
    private val viewModel by viewModels<LocationsViewModel>()
    private var mapView: MapView? = null

    private val locationsAdapter by lazy {
        LocationsRecyclerAdapter {
            findNavController().navigateSafe(
                LocationsFragmentDirections.openMenuFragment(it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        init()
        initObservers()
        initRecyclerview()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    private fun init() {
        requestLocationPermission()
        viewModel.getUserLocation()
        viewModel.getLocations()
        binding.btnToMap.onClick {
            findNavController().navigateSafe(LocationsFragmentDirections.openMapFragment())
        }
    }

    private fun initObservers() {
        viewModel.uiState.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: LocationsViewState) {
        when (state.event) {
            is LocationsEvent.UpdateLocations -> updateList(state)
            else -> {}
        }
    }

    private fun initRecyclerview() = with(binding) {
        val recyclerView: RecyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = locationsAdapter
    }

    private fun updateList(state: LocationsViewState) {
        state.locations?.let {
            locationsAdapter.setData(
                it,
                viewModel.showSearchResults.value?.latitude,
                viewModel.showSearchResults.value?.longitude
            )
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                PERMISSION_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(PERMISSION_FINE_LOCATION),
                PERMISSIONS_REQUEST_FINE_LOCATION
            )
        }
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        const val PERMISSIONS_REQUEST_FINE_LOCATION = 1
        const val PERMISSION_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION"
    }
}