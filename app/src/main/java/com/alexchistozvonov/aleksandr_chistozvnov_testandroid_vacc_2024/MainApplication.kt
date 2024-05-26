package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024

import android.app.Application
import android.content.Context
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di.AppComponent
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.constants.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    val Context.appComponent: AppComponent
        get() = when (this) {
            is MainApplication -> appComponent
            else -> (applicationContext as MainApplication).appComponent
        }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.API_KEY)
    }
}