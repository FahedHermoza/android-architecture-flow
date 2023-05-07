package com.fahedhermoza.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahedhermoza.kotlinapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.core.data.DataType
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
import com.fahedhermoza.kotlinapp.domain.model.Product
import com.fahedhermoza.kotlinapp.domain.usecase.product.UpdateProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.user.GetSessionUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProductViewModel(
    private val updateProductUseCase: UpdateProductUseCase,
    private val getSessionUseCase: GetSessionUseCase
) : BaseViewModel() {
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean?> get() = _loadingLiveData

    val onSuccess = SingleLiveEvent<Product?>()

    private val token by lazy {
        getSessionUseCase() ?: ""
    }

    fun editProduct(title: String, cost: Double, product: Product) = launch {
        product.name = title
        product.cost = cost

        val params = UpdateProductUseCase.UpdateProductUseCaseParams(token, product)
        updateProductUseCase.invoke(params).collect { dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val data = dataState.data
                    onSuccess.postValue(data)
                }

                DataType.Error -> {
                    _onError.postValue("Ocurrió un error ${dataState.code}")
                    Timber.i("Error logueo: ${dataState.message}")
                }
            }
        }
    }
}