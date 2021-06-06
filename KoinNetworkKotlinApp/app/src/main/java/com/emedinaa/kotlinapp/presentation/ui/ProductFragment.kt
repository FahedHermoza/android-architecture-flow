package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseBindingFragment
import com.emedinaa.kotlinapp.databinding.FragmentLoginBinding
import com.emedinaa.kotlinapp.databinding.FragmentProductBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment : BaseBindingFragment<FragmentProductBinding>(R.layout.fragment_product) {

    private  val args: ProductFragmentArgs by navArgs()
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
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it)
            }
        })

        viewModel.onProducts.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isEmpty()) {
                    binding.imageViewEmpty.visibility = View.VISIBLE
                } else {
                    binding.imageViewEmpty.visibility = View.GONE
                    adapter.update(it)
                }
            }
        })

        viewModel.onProductEmpty.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    adapter.clear()
                    viewModel.loadProducts()
                }
            }
        })

        viewModel.onSuccessDeleteAll.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it)
            }
        })

        viewModel.loadingLiveData.observe(viewLifecycleOwner,{
            if(it == true){
                showProgressBar()
            }else{
                hideProgressBar()
            }
        })
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
        findNavController().navigate(ProductFragmentDirections.actionProductFragmentToEditProductFragment(PRODUCT = product))
    }

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_product, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_all_delete){
            viewModel.deleteAllProducts()
        }
        return false
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar(){
        binding.pbProducts.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.pbProducts.visibility = View.GONE
    }
}