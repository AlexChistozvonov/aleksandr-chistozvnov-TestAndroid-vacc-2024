package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations

import androidx.recyclerview.widget.DiffUtil
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse

class DiffUtilLocations(
    private val oldList: List<LocationsResponse>,
    private val newList: List<LocationsResponse>
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

            oldList[oldItemPosition].point.latitude != newList[newItemPosition].point.latitude -> {
                false
            }

            oldList[oldItemPosition].point.longitude != newList[newItemPosition].point.longitude -> {
                false
            }

            else -> true
        }
    }
}
