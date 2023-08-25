package com.fahed.composeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahed.composeapp.core.data.DataType
import com.fahed.composeapp.domain.model.MultipleDelete
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.domain.usecase.product.ClearProductUseCase
import com.fahed.composeapp.domain.usecase.product.FetchProductUseCase
import com.fahed.composeapp.domain.usecase.user.GetSessionUseCase
import com.fahed.networkapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
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
                else -> {
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
                else -> {
                    _onError.postValue("Ocurrió un error ${dataState.code}")
                    Timber.i("Error logueo: ${dataState.message}")
                }
            }
        }
    }
}