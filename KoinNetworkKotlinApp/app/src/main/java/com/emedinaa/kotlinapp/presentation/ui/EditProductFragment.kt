package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentEditProductBinding
import com.emedinaa.kotlinapp.databinding.FragmentProductBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.UtilsAlertDialog
import com.emedinaa.kotlinapp.presentation.viewmodel.EditProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class EditProductFragment : BaseBindingFragment<FragmentEditProductBinding>(R.layout.fragment_edit_product) {
    private  val args: EditProductFragmentArgs by navArgs()
    private val viewModel: EditProductViewModel by viewModel()
    private var product: Product? = null

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun init() {
        product = args.PRODUCT
        binding.btnEditProduct.setOnClickListener {
            var title = binding.etTitleEdit.text.toString()
            var cost = binding.etCostEdit.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                product?.let {
                    viewModel.editProduct(title, cost, it)
                }
            }
        }
        render()
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

    private fun render() {
        product?.let {
            binding.etTitleEdit.setText(it.name)
            binding.etCostEdit.setText(it.cost.toString())
        }

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