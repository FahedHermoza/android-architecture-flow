package com.fahedhermoza.kotlinapp

import android.app.Application
import com.fahedhermoza.kotlinapp.core.utils.log.TimberFactory.setupOnDebug
import com.fahedhermoza.kotlinapp.di.networkModule
import com.fahedhermoza.kotlinapp.di.repositoryModule
import com.fahedhermoza.kotlinapp.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProductApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupOnDebug()
        startKoin {
            androidLogger(Level.ERROR)
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