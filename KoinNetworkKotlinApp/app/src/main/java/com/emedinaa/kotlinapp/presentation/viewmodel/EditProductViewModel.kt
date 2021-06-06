package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.product.UpdateProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.launch

class EditProductViewModel(private val updateProductUseCase: UpdateProductUseCase,
                           private val getSessionUseCase: GetSessionUseCase): ViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    val onSuccess = SingleLiveEvent<Product>()

    private val token by lazy {
        getSessionUseCase()?:""
    }

    fun editProduct(title:String, cost: Double, product:Product) = viewModelScope.launch {
        product.name = title
        product.cost = cost
        when(val result = updateProductUseCase.invoke(token, product)){
            is StorageResult.Complete -> {
                result.data?.let {
                    onSuccess.value = it
                }
            }
            is StorageResult.Failure -> { _onError.value = result.exception?.message?:"Ocurrió un error" }
            else -> { _onError.value = "Error 401...unauthorized" }
        }
    }
}