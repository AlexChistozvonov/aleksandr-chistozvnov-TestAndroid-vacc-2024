package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.hide
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.navigateSafe
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.onClick
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.show
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.MenuFragmentBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations.LocationsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.menu_fragment) {
    private val binding by viewBinding(MenuFragmentBinding::bind)
    private val viewModel by viewModels<MenuViewModel>()
    private val args: MenuFragmentArgs by navArgs()

    private val menuAdapter by lazy { MenuRecyclerAdapter {
        findNavController().navigateSafe(
            MenuFragmentDirections.openCartFragment(it)
        )
    } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
        initRecyclerview()
    }

    private fun init() {
        viewModel.getMenu(args.location.id)
    }

    private fun initObservers() {
        viewModel.uiState.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: MenuViewState) = with(binding) {
        when (state.event) {
            is MenuEvent.Success -> {
                progressBar.hide()
                btnToPayment.show()
                recyclerview.show()
                updateList(state)
                binding.btnToPayment.onClick {
                    findNavController().navigateSafe(MenuFragmentDirections.openOrderFragment(state.menu?.toTypedArray()))
                }
            }

            is MenuEvent.Loading -> {
                progressBar.show()
                btnToPayment.hide()
                recyclerview.hide()
            }

            else -> {}
        }
    }

    private fun initRecyclerview() = with(binding) {
        val recyclerView: RecyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = menuAdapter
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun updateList(state: MenuViewState) {
        state.menu?.let { menuAdapter.setData(it) }
    }
}