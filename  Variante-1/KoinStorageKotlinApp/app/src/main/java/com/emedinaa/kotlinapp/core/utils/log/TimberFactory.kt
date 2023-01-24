package com.emedinaa.kotlinapp.core.utils.log

import com.emedinaa.kotlinapp.BuildConfig.DEBUG
import timber.log.Timber



object TimberFactory {

    fun setupOnDebug(){
        Timber.uprootAll()
        if(DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}