package com.emedinaa.kotlinapp.presentation.ui

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentAddProductBinding
import com.emedinaa.kotlinapp.presentation.UtilsAlertDialog
import com.emedinaa.kotlinapp.presentation.viewmodel.AddProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductFragment : BaseBindingFragment<FragmentAddProductBinding>(R.layout.fragment_add_product) {
    private val viewModel: AddProductViewModel by viewModel()

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun init() {
        binding.btnAddProduct.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var cost = binding.etCost.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                viewModel.addProduct(title, cost)
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
                findNavController().popBackStack()
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

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun isValidate(title: String, cost: Double): Boolean {
        if(title.isNullOrEmpty())
            return false

        if(cost<0.0)
            return false

        return true
    }

    private fun showAlertProgress(){
        dialog.show()
    }

    private fun hideAlertProgress(){
        dialog.hide()
    }
}