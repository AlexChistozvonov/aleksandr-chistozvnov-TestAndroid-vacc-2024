package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.start

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.constants.PreferencesKey
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartScreenFragment : Fragment(R.layout.start_screen_fragment) {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        navigateToApp()
    }

    private fun navigateToApp() {
        val token = sharedPreferences.getString(PreferencesKey.ACCESS_TOKEN, "")
        if (token.isNullOrEmpty()) {
            findNavController().navigateSafe(StartScreenFragmentDirections.openRegistrationFragment())
        } else {
            findNavController().navigateSafe(StartScreenFragmentDirections.openLocationsFragment())
        }
    }
}