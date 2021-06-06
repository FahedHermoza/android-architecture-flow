package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinapp.data.StorageResult
import com.emedinaa.kotlinapp.domain.model.User
import com.emedinaa.kotlinapp.domain.usecase.user.AuthenticateUserUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.SaveSessionUseCase
import com.emedinaa.kotlinapp.presentation.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val authenticationUserUseCase: AuthenticateUserUseCase,
                    private val saveSessionUseCase: SaveSessionUseCase): ViewModel() {
    val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    val onSuccess = SingleLiveEvent<User>()

    fun login(username: String?, password: String?) = viewModelScope.launch {
        when(val result =authenticationUserUseCase.invoke(username,password)){
            is StorageResult.Complete -> {
                result.data?.let { itUser->
                    saveSessionUseCase.invoke(itUser.email,itUser.token, itUser.objectId)
                    onSuccess.value = itUser
                }
            }
            is StorageResult.Failure -> {
                _onError.value = result.exception?.message?:"Ocurrió un error"
            }
            else -> {
                _onError.value = "Error 401...unauthorized"
            }
        }
    }
}