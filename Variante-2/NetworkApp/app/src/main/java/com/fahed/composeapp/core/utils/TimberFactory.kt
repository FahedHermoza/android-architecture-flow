package com.fahed.networkapp.core.utils

// import com.fahed.networkapp.BuildConfig.DEBUG
import timber.log.Timber

object TimberFactory {

    fun setupOnDebug() {
        Timber.uprootAll()
        // if(DEBUG){
        Timber.plant(Timber.DebugTree())
        // }
    }
}