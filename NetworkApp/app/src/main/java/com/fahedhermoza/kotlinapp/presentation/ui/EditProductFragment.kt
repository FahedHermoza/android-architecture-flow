package com.fahedhermoza.kotlinapp.presentation.ui

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fahedhermoza.kotlinapp.R
import com.fahedhermoza.kotlinapp.core.base.BaseBindingFragment
import com.fahedhermoza.kotlinapp.databinding.FragmentEditProductBinding
import com.fahedhermoza.kotlinapp.domain.model.Product
import com.fahedhermoza.kotlinapp.presentation.viewmodel.EditProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EditProductFragment :
    BaseBindingFragment<FragmentEditProductBinding>(R.layout.fragment_edit_product) {
    private val args: EditProductFragmentArgs by navArgs()
    private val viewModel: EditProductViewModel by viewModel()
    private var product: Product? = null

    override fun init() {
        product = args.PRODUCT
        binding.btnEditProduct.setOnClickListener {
            var title = binding.etTitleEdit.text.toString()
            var cost = binding.etCostEdit.text.toString().toDoubleOrNull() ?: 0.0

            if (isValidate(title, cost)) {
                product?.let {
                    viewModel.editProduct(title, cost, it)
                }
            }
        }
        render()
    }

    override fun initViewModel() {
        viewModel.onError.observeNotNull { showMessage(it) }
        viewModel.onSuccess.observeNotNull { findNavController().popBackStack() }
        viewModel.loadingLiveData.observeNotNull { showAlertProgressLoading(it) }
    }

    private fun render() {
        product?.let {
            binding.etTitleEdit.setText(it.name)
            binding.etCostEdit.setText(it.cost.toString())
        }

    }

    private fun isValidate(title: String, cost: Double): Boolean {
        if (title.isNullOrEmpty())
            return false

        if (cost < 0.0)
            return false

        return true
    }
}