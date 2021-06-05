package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.*
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.launch

class ProductViewModel(private val fetchProductUseCase: FetchProductUseCase,
                       private val clearProductUseCase: ClearProductUseCase,
                       private val addProductUseCase: AddProductUseCase,
                       private val updateProductUseCase: UpdateProductUseCase
): ViewModel() {

    private val _products = MutableLiveData <LiveData<List<Product>>>()
    val onProducts = _products

    init{
        loadProducts()
    }

    fun loadProducts():LiveData<List<Product>> = fetchProductUseCase.invoke().asLiveData()

    fun addNewProduct(title:String, cost: Double, description: String) = viewModelScope.launch {
        var product = Product(0, title, cost, description, R.mipmap.ic_funko)
        addProductUseCase.invoke(product)
    }

    fun editProduct(title:String, cost: Double, product:Product)= viewModelScope.launch {
        product.name = title
        product.cost = cost
        updateProductUseCase.invoke(product)
    }
    
    fun deleteAllProducts() = viewModelScope.launch {
        clearProductUseCase.invoke()
    }
}