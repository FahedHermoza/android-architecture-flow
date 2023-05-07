package com.fahedhermoza.kotlinapp.presentation.viewmodel

import androidx.lifecycle.*
import com.fahedhermoza.kotlinapp.R
import com.fahedhermoza.kotlinapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.domain.model.Product
import com.fahedhermoza.kotlinapp.domain.usecase.AddProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.ClearProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.FetchProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductViewModel(
    private val fetchProductUseCase: FetchProductUseCase,
    private val clearProductUseCase: ClearProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : BaseViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val onProducts: LiveData<List<Product>> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() = launch {
        fetchProductUseCase.invoke().collect {
            _products.postValue(it)
        }
    }

    fun addNewProduct(title: String, cost: Double, description: String) = launch {
        var product = Product(0, title, cost, description, R.mipmap.ic_funko)
        val params = AddProductUseCase.Params(product)
        addProductUseCase.invoke(params)
    }

    fun editProduct(title: String, cost: Double, product: Product) = launch {
        product.name = title
        product.cost = cost
        val params = UpdateProductUseCase.Params(product)
        updateProductUseCase.invoke(params)
    }

    fun deleteAllProducts() = launch {
        clearProductUseCase.invoke()
    }
}