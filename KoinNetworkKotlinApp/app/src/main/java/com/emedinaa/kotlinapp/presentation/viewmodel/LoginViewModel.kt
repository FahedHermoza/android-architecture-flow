package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emedinaa.kotlinapp.core.base.BaseViewModel
import com.emedinaa.kotlinapp.core.data.DataType
import com.emedinaa.kotlinapp.domain.model.User
import com.emedinaa.kotlinapp.domain.usecase.user.AuthenticateUserUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.SaveSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val authenticationUserUseCase: AuthenticateUserUseCase,
                    private val saveSessionUseCase: SaveSessionUseCase): BaseViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean?> get() = _loadingLiveData

    val onSuccess = SingleLiveEvent<User?>()

    fun login(username: String?, password: String?) = launch {
        val params = AuthenticateUserUseCase.AuthenticateUserUseCaseParams(username, password)
        authenticationUserUseCase.invoke(params).collect{ dataState ->
            _loadingLiveData.postValue(dataState.loading)
            when(dataState.type){
                DataType.Success -> {
                    val data = dataState.data
                    val email = data?.email ?: ""
                    val token = data?.token ?:  ""
                    val objectId = data?.objectId ?:""
                    val params = SaveSessionUseCase.SaveSessionUseCaseParams(email, token, objectId)
                    saveSessionUseCase.invoke(params)
                    onSuccess.postValue(data!!)  //Resivar
                }

                DataType.Error -> {
                    //Resivar cuando se obtiene:
                    // 401 Unauthorized
                    // {"code":3003,"message":"Invalid login or password","errorData":{}}
                    _onError.postValue( dataState.message.toString())
                    Timber.e("Error logueo: ${dataState.message}")
                }
            }
        }

    }
}