package com.fahed.composeapp

import android.app.Application
import com.fahed.composeapp.di.networkModule
import com.fahed.composeapp.di.repositoryModule
import com.fahed.composeapp.di.viewmodelModule
import com.fahed.networkapp.core.utils.TimberFactory.setupOnDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ProductApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupOnDebug()
        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@ProductApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewmodelModule,
                    networkModule
                )
            )
        }
    }
}