package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse

class OrderRecyclerAdapter() :
    RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder>() {

    private var oldOrderList = arrayOf<MenuResponse>()


    fun setData(newOrderList: Array<MenuResponse>) {
        val diffUtil = DiffUtilOrder(oldOrderList, newOrderList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldOrderList = newOrderList
        diffResults.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tv_title)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val plus: ImageButton = itemView.findViewById(R.id.plus)
        val minus: ImageButton = itemView.findViewById(R.id.minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_order, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = with(holder) {
        name.text = oldOrderList[position].name
        price.text = holder.itemView.context.getString(
            R.string.menu_price,
            oldOrderList[position].price.toString()
        )
        plus.setOnClickListener {
            oldOrderList[position].quantity++
            quantity.text = oldOrderList[position].quantity.toString()
        }
        quantity.text = oldOrderList[position].quantity.toString()
        minus.setOnClickListener {
            if (oldOrderList[position].quantity > 0) oldOrderList[position].quantity--
            quantity.text = oldOrderList[position].quantity.toString()
        }
        quantity.text = oldOrderList[position].quantity.toString()
    }

    override fun getItemCount() = oldOrderList.size
}