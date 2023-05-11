package com.fahed.composeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahed.composeapp.core.data.DataType
import com.fahed.composeapp.domain.model.User
import com.fahed.composeapp.domain.usecase.user.AuthenticateUserUseCase
import com.fahed.composeapp.domain.usecase.user.SaveSessionUseCase
import com.fahed.networkapp.core.base.BaseViewModel
import com.fahed.networkapp.core.base.ErrorHttpException
import com.fahedhermoza.kotlinapp.core.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel (
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
                else -> {}
            }
        }
    }

    fun checkInvalidUsername(username: String): Boolean {
        return username.isEmpty()
    }

    fun checkInvalidPassword(password: String): Boolean {
        return password.isEmpty() or !regexPassword(password)
    }

    /***
     * Password must be at least 8 characters long, contain at least one letter and one number
     */
    private fun regexPassword(password: String?): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        return regex.matches(password.toString())
    }
}