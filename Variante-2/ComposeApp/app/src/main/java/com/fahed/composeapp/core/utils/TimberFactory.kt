package com.fahed.composeapp.core.utils

import com.fahed.composeapp.BuildConfig.DEBUG
import timber.log.Timber



object TimberFactory {

    fun setupOnDebug(){
        Timber.uprootAll()
        if(DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}