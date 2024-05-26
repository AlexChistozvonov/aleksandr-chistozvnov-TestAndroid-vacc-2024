package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion

import android.view.View
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.SafeClickListener

fun View.show() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.onClick(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}