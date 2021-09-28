package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentProductBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment : BaseBindingFragment<FragmentProductBinding>(R.layout.fragment_product) {

    private val viewModel: ProductViewModel by viewModel()
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true) //Enable menu
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        binding.fabProduct.setOnClickListener {
            goToAddProduct()
        }
        setupView()
    }

    override fun initViewModel() {
        viewModel.onError.observeNotNull { showMessage(it) }
        viewModel.onProducts.observe {
            it?.let {
                if (it.isEmpty()) {
                    binding.imageViewEmpty.visibility = View.VISIBLE
                } else {
                    binding.imageViewEmpty.visibility = View.GONE
                    adapter.update(it)
                }
            }
        }
        viewModel.onProductEmpty.observeNotNull {
            if (it) {
                adapter.clear()
                viewModel.loadProducts()
            }
        }
        viewModel.onSuccessDeleteAll.observeNotNull { showMessage(it) }
        viewModel.loadingLiveData.observeNotNull { showProgressBarLoading(it, binding.pbProducts) }
    }

    private fun goToAddProduct() {
        findNavController().navigate(ProductFragmentDirections.actionProductFragmentToAddProductFragment())
    }

    private fun setupView() {
        viewModel.loadProducts()

        adapter = ProductsAdapter(emptyList(), onItemAction())
        binding.rvProduct.adapter = adapter
    }

    private fun onItemAction(): (item: Product) -> Unit {
        return {
            goToEditProduct(it)
        }
    }

    private fun goToEditProduct(product: Product) {
        findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToEditProductFragment(
                PRODUCT = product
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_product, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_all_delete) {
            viewModel.deleteAllProducts()
        }
        return false
    }
}