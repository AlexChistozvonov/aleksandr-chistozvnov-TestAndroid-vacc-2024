package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.getBitmapFromDrawable
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.MapFragmentBinding
import com.example.sevenwindsstudiotest.presentation.map.MapEvent
import com.example.sevenwindsstudiotest.presentation.map.MapViewModel
import com.example.sevenwindsstudiotest.presentation.map.MapViewState
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.map_fragment) {

    private val binding by viewBinding(MapFragmentBinding::bind)
    private val viewModel by viewModels<MapViewModel>()
    private var mapView: MapView? = null

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        init()
        initObservers()
    }

    private fun init() {
        viewModel.getLocations()
    }

    private fun initObservers() {
        viewModel.uiState.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun moveCamera(point: Point) {
        mapView?.map?.move(
            CameraPosition(point, 8f, 0.0f, 0.0f)
        )
    }

    private fun handleState(state: MapViewState) {
        when (state.event) {
            is MapEvent.UpdateLocations -> initViewList(state)
            else -> {}
        }
    }

    private fun initViewList(state: MapViewState) {
        state.locations?.let {
            moveCamera(Point(it[0].point.latitude, it[0].point.longitude))
            for (i in it.indices) {
                addPlacemark(Point(it[i].point.latitude, it[i].point.longitude))
            }
        }

    }

    private fun addPlacemark(point: Point) {
        val imageProvider = ImageProvider.fromBitmap(
            getBitmapFromDrawable(requireContext(), R.drawable.ic_pin)
        )
        mapView?.map?.mapObjects?.addPlacemark(point, imageProvider)
    }

    override fun onStop() {
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}