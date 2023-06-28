package com.fahed.composeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahed.composeapp.core.data.DataType
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.domain.usecase.product.UpdateProductUseCase
import com.fahed.composeapp.domain.usecase.user.GetSessionUseCase
import com.fahed.networkapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProductViewModel(
    private val updateProductUseCase: UpdateProductUseCase,
    private val getSessionUseCase: GetSessionUseCase
) : BaseViewModel() {
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean?> get() = _onLoading

    val onSuccess = SingleLiveEvent<Product?>()

    private val token by lazy {
        getSessionUseCase() ?: ""
    }

    fun editProduct(title: String, cost: Double, product: Product) = launch {
        product.name = title
        product.cost = cost

        val params = UpdateProductUseCase.UpdateProductUseCaseParams(token, product)
        updateProductUseCase.invoke(params).collect { dataState ->
            _onLoading.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val data = dataState.data
                    onSuccess.postValue(data)
                }

                DataType.Error -> {
                    _onError.postValue("Ocurrió un error ${dataState.code}")
                    Timber.i("Error logueo: ${dataState.message}")
                }
                else -> {}
            }
        }
    }
}