package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.databinding.FragmentProductBinding
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModel()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabProduct.setOnClickListener {
            goToAddProduct()
        }

        setupView()
        setObservers()
    }

    private fun goToAddProduct() {
        findNavController().navigate(R.id.action_productFragment_to_addProductFragment)
    }

    private fun setupView() {
        viewModel.loadProducts()
        showProgressBar()

        adapter = ProductsAdapter(emptyList(), onItemAction())
        binding.rvProduct.adapter = adapter
    }

    private fun onItemAction(): (item: Product) -> Unit {
        return {
            goToEditProduct(it)
        }
    }

    private fun goToEditProduct(product: Product) {
        findNavController().navigate(R.id.action_productFragment_to_editProductFragment, Bundle().apply {
            putSerializable("PRODUCT", product)
        })
    }

    private fun setObservers(){
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it)
            }
            hideProgressBar()
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
            hideProgressBar()
        })

        viewModel.onProductEmpty.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    adapter.clear()
                    viewModel.loadProducts()
                }
            }
            hideProgressBar()
        })

        viewModel.onSuccessDeleteAll.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it)
            }
        })
    }

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_product, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_all_delete){
            showProgressBar()
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