package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.*
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseViewModel
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.flow.asFlow
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

    fun loadProducts() = launch {
        fetchProductUseCase.invoke().collect {
            _products.postValue(it)
        }
    }

    fun addNewProduct(title: String, cost: Double, description: String) = launch {
        var product = Product(0, title, cost, description, R.mipmap.ic_funko)
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
}