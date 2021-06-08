package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinapp.core.base.BaseViewModel
import com.emedinaa.kotlinapp.core.data.DataType
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.product.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetObjectIdUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class AddProductViewModel(private val addProductUseCase: AddProductUseCase,
                            private val getSessionUseCase: GetSessionUseCase,
                            private val getObjectIdUseCase: GetObjectIdUseCase): BaseViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean?> get() = _loadingLiveData

    val onSuccess = SingleLiveEvent<Product?>()

    private val token by lazy {
        getSessionUseCase()?:""
    }

    private val objectId by lazy {
        getObjectIdUseCase.invoke()?:""
    }

    fun addProduct(title:String, cost:Double) = launch {
        val product = Product("", title, "", cost, "",objectId)
        val params = AddProductUseCase.AddProductUseCaseParams(token, product)
        addProductUseCase.invoke(params).collect{ dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when(dataState.type){
                DataType.Success -> {
                    val data = dataState.data
                    onSuccess.postValue(data!!)
                }

                DataType.Error -> {
                    _onError.postValue( "Ocurrió un error ${dataState.code }")
                    Timber.i("Error logueo: ${dataState.message}")
                }
            }
        }
    }
}