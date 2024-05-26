package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.CartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment(R.layout.cart_fragment) {
    private val binding by viewBinding(CartFragmentBinding::bind)
    private val viewModel by viewModels<CartViewModel>()
}