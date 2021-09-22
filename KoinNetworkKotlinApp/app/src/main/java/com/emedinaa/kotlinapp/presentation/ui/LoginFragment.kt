package com.emedinaa.kotlinapp.presentation.ui

import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentLoginBinding
import com.emedinaa.kotlinapp.domain.model.User
import com.emedinaa.kotlinapp.presentation.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModel()

    override fun init() {
        binding.textInputLayoutUser.editText?.setText("admin@admin.com")
        binding.buttonLogin.setOnClickListener {
            if (validateForm()) {
                viewModel.login(
                    binding.textInputLayoutUser.editText?.text.toString(),
                    binding.textInputLayoutPassword.editText?.text.toString()
                )
            }
        }
    }

    override fun initViewModel() {
        viewModel.onError.observeNotNull { showError(it) }
        viewModel.onSuccess.observeNotNull { gotoProduct(it) }
        viewModel.loadingLiveData.observeNotNull { showAlertProgressLoading(it) }
    }

    private fun validateForm(): Boolean {
        binding.textInputLayoutUser.error = null
        binding.textInputLayoutPassword.error = null

        if (binding.textInputLayoutUser.editText?.text.toString().isEmpty()) {
            return false
        }

        if (binding.textInputLayoutPassword.editText?.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    private fun gotoProduct(user: User) {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToProductFragment(
                USER = user
            )
        )
    }
}