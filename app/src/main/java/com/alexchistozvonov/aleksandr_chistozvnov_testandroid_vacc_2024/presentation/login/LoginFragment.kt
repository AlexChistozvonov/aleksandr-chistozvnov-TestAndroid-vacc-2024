package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.R
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.hide
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.navigateSafe
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.onClick
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.extantion.show
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val binding by viewBinding(LoginFragmentBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        etEmail.doOnTextChanged { text, _, _, _ -> viewModel.onEmailTextChanged(text.toString()) }
        etPassword.doOnTextChanged { text, _, _, _ -> viewModel.onPasswordTextChanged(text.toString()) }
        btnLogin.onClick { viewModel.login() }
    }

    private fun initObservers() {
        viewModel.uiState.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: LoginViewState) = with(binding) {
        fun showView() {
            progressBar.hide()
            tvEmail.show()
            tvPassword.show()
            tilEmail.show()
            tilPassword.show()
            etEmail.show()
            etPassword.show()
            btnLogin.show()
        }
        when (state.event) {
            LoginEvent.Success -> {
                showView()
                findNavController().navigateSafe(
                    LoginFragmentDirections.openLocationsFragment()
                )
            }

            LoginEvent.Loading -> {
                progressBar.show()
                tvEmail.hide()
                tvPassword.hide()
                tilEmail.hide()
                tilPassword.hide()
                etEmail.hide()
                etPassword.hide()
                btnLogin.hide()
            }

            is LoginEvent.Error -> {
                showView()
                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }
}
