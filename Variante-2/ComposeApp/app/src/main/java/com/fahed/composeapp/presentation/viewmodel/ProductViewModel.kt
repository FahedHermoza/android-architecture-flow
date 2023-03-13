package com.fahed.composeapp.presentation.viewmodel

import androidx.lifecycle.*
import com.fahed.composeapp.R
import com.fahed.composeapp.core.base.BaseViewModel
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.domain.usecase.AddProductUseCase
import com.fahed.composeapp.domain.usecase.ClearProductUseCase
import com.fahed.composeapp.domain.usecase.FetchProductUseCase
import com.fahed.composeapp.domain.usecase.GetProductUseCase
import com.fahed.composeapp.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.launch

class ProductViewModel(
    private val fetchProductUseCase: FetchProductUseCase,
    private val clearProductUseCase: ClearProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val getProductUseCase: GetProductUseCase
) : BaseViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val onProducts: LiveData<List<Product>> = _products

    private val _productSelected = MutableLiveData<Product>()
    val onProductSelected: LiveData<Product> = _productSelected

    init {
        loadProducts()
    }

    private fun loadProducts() = launch {
        fetchProductUseCase.invoke().collect {
            _products.postValue(it)
        }
    }


    fun addProduct(title: String, cost: Double, description: String) = launch {
        var product = Product(0, title, cost, description, R.drawable.ic_funko)
        val params = AddProductUseCase.AddProductUseCaseParams(product)
        addProductUseCase.invoke(params)
    }

    fun editProduct(title: String, cost: Double, product: Product) = launch {
        product.name = title
        product.cost = cost
        val params = UpdateProductUseCase.UpdateProductUseCaseParams(product)
        updateProductUseCase.invoke(params)
    }

    fun deleteAllProducts() = launch {
        clearProductUseCase.invoke()
    }

    fun getProduct(id: Int) = launch {
        val params = GetProductUseCase.GetProductUseCaseParams(id)
        val product = getProductUseCase.invoke(params)
        _productSelected.postValue(product)
    }
}