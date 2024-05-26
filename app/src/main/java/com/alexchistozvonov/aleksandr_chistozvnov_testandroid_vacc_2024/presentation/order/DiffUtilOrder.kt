package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.order

import androidx.recyclerview.widget.DiffUtil
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse

class DiffUtilOrder(
    private val oldList: Array<MenuResponse>,
    private val newList: Array<MenuResponse>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }

            oldList[oldItemPosition].imageURL != newList[newItemPosition].imageURL -> {
                false
            }

            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }

            oldList[oldItemPosition].quantity != newList[newItemPosition].quantity -> {
                false
            }

            else -> true
        }
    }
}
