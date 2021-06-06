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
import com.emedinaa.kotlinapp.databinding.FragmentLoginBinding
import com.emedinaa.kotlinapp.domain.model.User
import com.emedinaa.kotlinapp.presentation.UtilsAlertDialog
import com.emedinaa.kotlinapp.presentation.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

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

    private fun setObservers() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            hideAlertProgress()
            it?.let {
                showMessage(it)
            }

        })

        viewModel.onSuccess.observe(viewLifecycleOwner, Observer {
            hideAlertProgress()
            it?.let {
                gotoProduct(it)
            }
        })
    }

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun gotoProduct(user: User) {
        findNavController().navigate(R.id.action_loginFragment_to_productFragment,Bundle().apply {
            putSerializable("USER",user)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertProgress(){
        dialog.show()
    }

    private fun hideAlertProgress(){
        dialog.hide()
    }
}