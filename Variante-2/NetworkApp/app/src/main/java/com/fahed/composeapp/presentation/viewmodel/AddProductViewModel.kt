package com.fahed.composeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahed.composeapp.core.data.DataType
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.domain.usecase.product.AddProductUseCase
import com.fahed.composeapp.domain.usecase.user.GetObjectIdUseCase
import com.fahed.composeapp.domain.usecase.user.GetSessionUseCase
import com.fahed.networkapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class AddProductViewModel(
    private val addProductUseCase: AddProductUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val getObjectIdUseCase: GetObjectIdUseCase
) : BaseViewModel() {
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean?> get() = _onLoading

    val onSuccess = SingleLiveEvent<Product?>()

    private val token by lazy {
        getSessionUseCase() ?: ""
    }

    private val objectId by lazy {
        getObjectIdUseCase.invoke() ?: ""
    }

    fun addProduct(title: String, cost: Double) = launch {
        val product = Product("", title, "", cost, "", objectId)
        val params = AddProductUseCase.AddProductUseCaseParams(token, product)
        addProductUseCase.invoke(params).collect { dataState ->
            _onLoading.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val data = dataState.data
                    onSuccess.postValue(data!!)
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