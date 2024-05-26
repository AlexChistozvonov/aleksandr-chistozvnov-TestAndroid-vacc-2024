package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse
import com.bumptech.glide.Glide

class MenuRecyclerAdapter(private val listener: (id: MenuResponse) -> Unit) :
    RecyclerView.Adapter<MenuRecyclerAdapter.MyViewHolder>() {

    private var oldMenuList = listOf<MenuResponse>()

    fun setData(newMenuList: List<MenuResponse>) {
        val diffUtil = DiffUtilMenu(oldMenuList, newMenuList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldMenuList = newMenuList
        diffResults.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.tv_title)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val image: ImageView = itemView.findViewById(R.id.image)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val plus: ImageButton = itemView.findViewById(R.id.plus)
        val minus: ImageButton = itemView.findViewById(R.id.minus)

        override fun onClick(v: View?) {
            listener.invoke(oldMenuList[layoutPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_menu, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = with(holder) {
        name.text = oldMenuList[position].name
        price.text = holder.itemView.context.getString(
            R.string.menu_price,
            oldMenuList[position].price.toString()
        )
        Glide.with(itemView.context).load(oldMenuList[position].imageURL).into(image)
        plus.setOnClickListener {
            oldMenuList[position].quantity++
            quantity.text = oldMenuList[position].quantity.toString()
        }
        minus.setOnClickListener {
            if (oldMenuList[position].quantity > 0) oldMenuList[position].quantity--
            quantity.text = oldMenuList[position].quantity.toString()
        }
    }

    override fun getItemCount() = oldMenuList.size
}