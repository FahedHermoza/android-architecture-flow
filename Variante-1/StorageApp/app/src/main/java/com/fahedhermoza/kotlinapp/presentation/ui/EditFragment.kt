package com.fahedhermoza.kotlinapp.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fahedhermoza.kotlinapp.R
import com.fahedhermoza.kotlinapp.core.base.BaseBindingFragment
import com.fahedhermoza.kotlinapp.databinding.FragmentEditBinding
import com.fahedhermoza.kotlinapp.domain.model.Product
import com.fahedhermoza.kotlinapp.presentation.viewmodel.ProductEvent
import com.fahedhermoza.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EditFragment : BaseBindingFragment<FragmentEditBinding>(R.layout.fragment_edit) {

    private  val args: EditFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by viewModel()

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //product = arguments?.getSerializable("PRODUCT") as? Product
        //product = Product(args.id, args.name, args.cost.toDouble(), args.description, args.logo)
        product = args.product
    }

    override fun init() {
        product = args.product
        render()

        binding.btnEditProduct.setOnClickListener {
            var title = binding.etTitleEdit.text.toString()
            var cost = binding.etCostEdit.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                product?.let {
                    viewModel.onEvent(ProductEvent.ActionEditProduct(title, cost, it))
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun initViewModel() {}

    private fun isValidate(title: String, cost: Double): Boolean {
        if (title.isNullOrEmpty())
            return false

        if (cost < 0.0)
            return false

        return true
    }

    private fun render() {
        product?.let {
            binding.etTitleEdit.setText(it.name)
            binding.etCostEdit.setText(it.cost.toString())
        }

    }
}