package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationsResponse(
    val id: Int,
    val name: String,
    val point: Point
) : Parcelable

@Parcelize
data class Point(
    val latitude: Double,
    val longitude: Double
) : Parcelable