package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emedinaa.kotlinapp.core.base.BaseViewModel
import com.emedinaa.kotlinapp.core.data.DataType
import com.emedinaa.kotlinapp.core.utils.livedata.SingleLiveEvent
import com.emedinaa.kotlinapp.domain.model.MultipleDelete
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.product.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.GetSessionUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel(
    private val fetchProductUseCase: FetchProductUseCase,
    private val clearProductUseCase: ClearProductUseCase,
    private val getSessionUseCase: GetSessionUseCase
) : BaseViewModel() {
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _products = MutableLiveData<List<Product>>()
    val onProducts: LiveData<List<Product>> = _products

    private val _productEmpty = MutableLiveData<Boolean>()
    val onProductEmpty: LiveData<Boolean?> = _productEmpty

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean?> get() = _loadingLiveData

    val onSuccessDeleteAll = SingleLiveEvent<String?>()

    private val token by lazy {
        getSessionUseCase() ?: ""
    }

    fun loadProducts() = launch {
        val params = FetchProductUseCase.FetchProductUseCaseParams(token)
        fetchProductUseCase.invoke(params).collect { dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val notes = dataState.data ?: emptyList()
                    _products.postValue(notes)
                }

                DataType.Error -> {
                    _onError.postValue("Ocurrió un error ${dataState.code}")
                    Timber.e("Error logueo: ${dataState.message}")
                }
            }
        }
    }

    fun deleteAllProducts() = launch {
        val minimalCost: Double = 0.0
        val params = ClearProductUseCase.ClearProductUseCaseParams(token, minimalCost)
        clearProductUseCase.invoke(params).collect { dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val multipleDelete = dataState.data ?: MultipleDelete(quantity = 0)
                    if (multipleDelete.quantity > 1) {
                        onSuccessDeleteAll.postValue("Productos eliminados.")
                    } else if (multipleDelete.quantity == 1) {
                        onSuccessDeleteAll.postValue("Producto eliminado.")
                    } else {
                        onSuccessDeleteAll.postValue("No se encontraron productos.")
                    }
                    _productEmpty.postValue(true)
                }

                DataType.Error -> {
                    _onError.postValue("Ocurrió un error ${dataState.code}")
                    Timber.i("Error logueo: ${dataState.message}")
                }
            }
        }
    }
}