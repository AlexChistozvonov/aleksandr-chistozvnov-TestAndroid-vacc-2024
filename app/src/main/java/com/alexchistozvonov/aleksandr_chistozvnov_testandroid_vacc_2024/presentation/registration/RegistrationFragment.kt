package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.presentation.registration

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
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.databinding.RegistrationFragmentBinding
import com.example.sevenwindsstudiotest.presentation.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.registration_fragment) {

    private val binding by viewBinding(RegistrationFragmentBinding::bind)
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        etEmail.doOnTextChanged { text, _, _, _ -> viewModel.onEmailTextChanged(text.toString()) }
        etPassword.doOnTextChanged { text, _, _, _ -> viewModel.onPasswordTextChanged(text.toString()) }
        etPasswordRepeat.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordTextChanged(
                text.toString(),
                true
            )
        }
        btnRegister.onClick {
            val password = etPassword.text.toString()
            val password2 = etPasswordRepeat.text.toString()
            if (password == password2) {
                viewModel.registration()
            } else {
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }
        tvHaveAccount.onClick {
            findNavController().navigateSafe(
                RegistrationFragmentDirections.openLoginFragment()
            )
        }
    }

    private fun initObservers() {
        viewModel.uiState.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: RegistrationViewState) = with(binding) {
        fun showView() {
            progressBar.hide()
            tvHaveAccount.show()
            tvEmail.show()
            tilPassword.show()
            tvPassword.show()
            tvPasswordRepeat.show()
            tilEmail.show()
            tilPasswordRepeat.show()
            btnRegister.show()
        }
        when (state.event) {
            RegistrationEvent.Success -> {
                showView()
                findNavController().navigateSafe(
                    RegistrationFragmentDirections.openLoginFragment()
                )
            }

            RegistrationEvent.Loading -> {
                progressBar.show()
                tvHaveAccount.hide()
                tvEmail.hide()
                tilPassword.hide()
                tvPassword.hide()
                tvPasswordRepeat.hide()
                tilEmail.hide()
                tilPasswordRepeat.hide()
                btnRegister.hide()
            }

            is RegistrationEvent.Error -> {
                showView()
                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }
}