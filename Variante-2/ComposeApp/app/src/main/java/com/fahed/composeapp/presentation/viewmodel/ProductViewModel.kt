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
    val onProducts: LiveData<List<Product>> get() = _products

    private val _productSelected = MutableLiveData(Product())
    val onProductSelected: LiveData<Product> get() = _productSelected

    suspend fun loadProducts() {
        fetchProductUseCase.invoke().collect {
            _products.postValue(it)
        }
    }

    fun addProduct(title: String, cost: Double, description: String) = launch {
        var product = Product(0, title, cost, description, R.drawable.ic_funko)
        val params = AddProductUseCase.AddProductUseCaseParams(product)
        addProductUseCase.invoke(params)
    }

    fun editProduct(product: Product) = launch {
        val params = UpdateProductUseCase.UpdateProductUseCaseParams(product)
        updateProductUseCase.invoke(params)
    }

    fun deleteAllProducts() = launch {
        clearProductUseCase.invoke()
    }

    suspend fun getProduct(id: Int) {
        val params = GetProductUseCase.GetProductUseCaseParams(id)
        val product = getProductUseCase.invoke(params)
        _productSelected.value = product
    }

    fun setProduct(onChangeData: () -> Product?){
        _productSelected.value = onChangeData()
    }
}