package com.emedinaa.kotlinapp.core.base

import androidx.lifecycle.ViewModel
import com.emedinaa.kotlinapp.core.utils.coroutine.ioScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.cancel
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), CoroutineScope by ioScope(){

    @InternalCoroutinesApi
    override fun onCleared() {
        try {
            cancel()
        } catch (e: Exception) {
            Timber.e(e, "The BaseViewModel CoroutineScope does not have an associated job to cancel...")
        }
        super.onCleared()
    }

}