package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.product.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetObjectIdUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.launch

class AddProductViewModel(private val addProductUseCase: AddProductUseCase,
                            private val getSessionUseCase: GetSessionUseCase,
                            private val getObjectIdUseCase: GetObjectIdUseCase): ViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    val onSuccess = SingleLiveEvent<Product>()

    private val token by lazy {
        getSessionUseCase()?:""
    }

    private val objectId by lazy {
        getObjectIdUseCase.invoke()?:""
    }

    fun addProduct(title:String, cost:Double) = viewModelScope.launch {
        val product = Product("", title, "", cost, "",objectId)

        when(val result = addProductUseCase.invoke(token, product)){
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