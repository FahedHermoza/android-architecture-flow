package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentLoginBinding
import com.emedinaa.kotlinapp.domain.model.User
import com.emedinaa.kotlinapp.presentation.UtilsAlertDialog
import com.emedinaa.kotlinapp.presentation.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModel()

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun init() {
        binding.textInputLayoutUser.editText?.setText("admin@admin.com")
        binding.buttonLogin.setOnClickListener {
            if(validateForm()){
                showAlertProgress()
                viewModel.login(
                        binding.textInputLayoutUser.editText?.text.toString(),
                        binding.textInputLayoutPassword.editText?.text.toString()
                )
            }
        }
    }

    override fun initViewModel() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it)
            }

        })

        viewModel.onSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                gotoProduct(it)
            }
        })

        viewModel.loadingLiveData.observe(viewLifecycleOwner,{
            if(it == true){
                showAlertProgress()
            }else{
                hideAlertProgress()
            }
        })
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

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun gotoProduct(user: User) {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProductFragment(USER = user))
    }

    private fun showAlertProgress() {
        dialog.show()
    }

    private fun hideAlertProgress() {
        dialog.hide()
    }
}