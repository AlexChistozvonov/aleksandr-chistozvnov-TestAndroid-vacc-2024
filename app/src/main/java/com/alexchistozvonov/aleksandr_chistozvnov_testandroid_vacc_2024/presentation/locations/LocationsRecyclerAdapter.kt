package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sqrt

class LocationsRecyclerAdapter(private val listener: (id: LocationsResponse) -> Unit) :
    RecyclerView.Adapter<LocationsRecyclerAdapter.MyViewHolder>() {

    private var oldLocationList = listOf<LocationsResponse>()
    private var userLatitude: Double = 0.0
    private var userLongitude: Double = 0.0

    fun setData(
        newLocationList: List<LocationsResponse>,
        userLatitude: Double?,
        userLongitude: Double?
    ) {
        val diffUtil = DiffUtilLocations(oldLocationList, newLocationList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldLocationList = newLocationList
        diffResults.dispatchUpdatesTo(this)
        if (userLatitude != null) {
            this.userLatitude = userLatitude
        }
        if (userLongitude != null) {
            this.userLongitude = userLongitude
        }

    }

    private fun calculationDistance(lat2: Double, lon2: Double): Double {
        return userLatitude.let { lat ->
            userLongitude.let { lon ->
                111.2 * sqrt(
                    (lon - lon2) * (lon - lon2) + (lat - lat2) * cos(
                        Math.PI * lon / 180
                    ) * (lat - lat2) * cos(
                        Math.PI * lon / 180
                    )
                )
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val locationName: TextView = itemView.findViewById(R.id.tv_title)
        val distance: TextView = itemView.findViewById(R.id.tv_distance)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.invoke(oldLocationList[layoutPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_location, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = with(holder) {
        locationName.text = oldLocationList[position].name
        distance.text = itemView.context.getString(R.string.location_distance, calculationDistance(
            oldLocationList[position].point.latitude,
            oldLocationList[position].point.longitude
        )?.let { round(it).toString() })
    }

    override fun getItemCount() = oldLocationList.size
}