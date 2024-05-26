package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.OrderFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.order_fragment) {
    private val binding by viewBinding(OrderFragmentBinding::bind)
    private val args: OrderFragmentArgs by navArgs()

    private val orderAdapter by lazy { OrderRecyclerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
    }

    private fun initRecyclerview() = with(binding) {
        val recyclerView: RecyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = orderAdapter
        updateList()
    }

    private fun updateList() {
        args.order?.let { orderAdapter.setData(it) }
    }

}