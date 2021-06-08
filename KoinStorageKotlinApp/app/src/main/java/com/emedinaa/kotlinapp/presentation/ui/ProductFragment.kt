package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentAddProductBinding
import com.emedinaa.kotlinapp.databinding.FragmentProductBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/***
 * https://developer.android.com/guide/navigation/navigation-pass-data
 */
class ProductFragment : BaseBindingFragment<FragmentProductBinding>(R.layout.fragment_product) {

    private val viewModel: ProductViewModel by viewModel()

    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        binding.fabProduct.setOnClickListener {
            goToAddProduct()
        }
        setupView()
    }

    override fun initViewModel() {
        viewModel.onProducts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.update(it)
            }
        })
    }

    private fun goToAddProduct() {
        findNavController().navigate(R.id.action_productFragment_to_addProductFragment)
    }

    private fun setupView() {
        adapter = ProductsAdapter(emptyList(), onItemAction())
        binding.rvProduct.adapter = adapter
    }

    private fun onItemAction(): (item: Product) -> Unit {
        return {
            goToEditProduct(it)
        }
    }

    private fun goToEditProduct(product: Product) {

        /*
        //Navigate with actions
        findNavController().navigate(R.id.action_productFragment_to_editFragment, Bundle().apply {
            putSerializable("PRODUCT", product)
        })*/

        //Navigation safe args
        findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToEditFragment(
                id = product.id,
                name = product.name,
                cost = product.cost.toString(),
                description = product.description,
                logo = product.logo,
                product = product
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_product, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_all_delete) {
            viewModel.deleteAllProducts()
            showToast("Productos eliminados")
        }
        return false
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}