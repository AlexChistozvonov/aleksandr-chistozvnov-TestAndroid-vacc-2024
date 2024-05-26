package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}