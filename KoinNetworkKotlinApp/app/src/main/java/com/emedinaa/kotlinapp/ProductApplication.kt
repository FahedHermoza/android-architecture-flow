package com.emedinaa.kotlinapp

import android.app.Application
import com.emedinaa.kotlinapp.di.networkModule
import com.emedinaa.kotlinapp.di.repositoryModule
import com.emedinaa.kotlinapp.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author Eduardo Medina
 */
class ProductApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ProductApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewmodelModule,
                    networkModule)
            )
        }
    }
}