package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.databinding.FragmentEditBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EditFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModel()

    private lateinit var binding: FragmentEditBinding

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getSerializable("PRODUCT") as? Product
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        render()

        binding.btnEditProduct.setOnClickListener {
            var title = binding.etTitleEdit.text.toString()
            var cost = binding.etCostEdit.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                product?.let {
                    viewModel.editProduct(title, cost, it)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun isValidate(title: String, cost: Double): Boolean {
        if(title.isNullOrEmpty())
            return false

        if(cost<0.0)
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