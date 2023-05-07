package com.fahedhermoza.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahedhermoza.kotlinapp.core.base.BaseViewModel
import com.fahedhermoza.kotlinapp.core.base.ErrorHttpException
import com.fahedhermoza.kotlinapp.core.data.DataType
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
import com.fahedhermoza.kotlinapp.domain.model.User
import com.fahedhermoza.kotlinapp.domain.usecase.user.AuthenticateUserUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.user.SaveSessionUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val authenticationUserUseCase: AuthenticateUserUseCase,
    private val saveSessionUseCase: SaveSessionUseCase
) : BaseViewModel() {
    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String?> = _onError

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean?> get() = _loadingLiveData

    val onSuccess = SingleLiveEvent<User?>()

    fun login(username: String?, password: String?) = launch {
        val params = AuthenticateUserUseCase.AuthenticateUserUseCaseParams(username, password)
        authenticationUserUseCase.invoke(params).collect { dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when (dataState.type) {
                DataType.Success -> {
                    val data = dataState.data
                    data?.let {
                        val email = data.email
                        val token = data.token
                        val objectId = data.objectId
                        val params =
                            SaveSessionUseCase.SaveSessionUseCaseParams(email, token, objectId)
                        saveSessionUseCase.invoke(params)
                        onSuccess.postValue(data)
                    } ?: run {
                        _onError.postValue("Empty data error")
                        Timber.e("Error response")
                    }
                }

                DataType.Error -> {
                    // 401 Unauthorized
                    // {"code":3003,"message":"Invalid login or password","errorData":{}}
                    var errorResponse =
                        ErrorHttpException.fromJsonString(dataState.errorBody.toString())
                    _onError.postValue(errorResponse.message)
                    Timber.e("Error logueo: ${dataState.message.toString()}")
                }
            }
        }

    }
}