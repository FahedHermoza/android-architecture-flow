package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentAddProductBinding
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AddProductFragment : BaseBindingFragment<FragmentAddProductBinding>(R.layout.fragment_add_product) {

    private val viewModel: ProductViewModel by viewModel()

    override fun init() {
        binding.btnAddProduct.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var cost = binding.etCost.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                viewModel.addNewProduct(title, cost, "LoremImpsun")
                findNavController().popBackStack()
            }
        }
    }

    override fun initViewModel() {}

    private fun isValidate(title: String, cost: Double): Boolean {
        if(title.isNullOrEmpty())
            return false

        if(cost<0.0)
            return false

        return true
    }

    private fun showMessageError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}