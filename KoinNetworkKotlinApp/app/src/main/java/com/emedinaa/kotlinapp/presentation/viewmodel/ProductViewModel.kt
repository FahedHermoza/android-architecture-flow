package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.product.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.launch

class ProductViewModel(private val fetchProductUseCase: FetchProductUseCase,
                       private val clearProductUseCase: ClearProductUseCase,
                       private val getSessionUseCase: GetSessionUseCase): ViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    private val _products = MutableLiveData <List<Product>>()
    val onProducts: LiveData<List<Product>> = _products

    private val _ProductEmpty = MutableLiveData <Boolean>()
    val onProductEmpty: LiveData<Boolean> = _ProductEmpty

    val onSuccessDeleteAll = SingleLiveEvent<String>()

    private val token by lazy {
        getSessionUseCase()?:""
    }

    fun loadProducts() = viewModelScope.launch {
        when (val result = fetchProductUseCase.invoke(token)) {
            is StorageResult.Success -> {
                val notes = result.data ?: emptyList()
                _products.value = notes
            }
            is StorageResult.Failure -> {
                _onError.value = result.exception?.message ?: "Ocurrió un error"
            }
        }
    }

    fun deleteAllProducts()= viewModelScope.launch {
        val minimalCost: Double = 0.0
        when (val result = clearProductUseCase.invoke(token, minimalCost)) {
            is StorageResult.Complete -> {
                result.data?.let {
                    val quantity = it.quantity
                    if(quantity > 1){
                        onSuccessDeleteAll.value = "Productos eliminados."
                    }else if(quantity == 1){
                        onSuccessDeleteAll.value = "Producto eliminado."
                    }else{
                        onSuccessDeleteAll.value = "No se encontraron productos."
                    }
                    _ProductEmpty.value = true
                }
            }
            is StorageResult.Failure -> {
                _onError.value = result.exception?.message ?: "Ocurrió un error"
            }
        }
    }
}