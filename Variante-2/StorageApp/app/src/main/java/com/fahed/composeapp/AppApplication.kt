package com.fahed.composeapp

import android.app.Application
import com.fahed.composeapp.core.utils.TimberFactory.setupOnDebug
import com.fahed.composeapp.di.dataBaseModule
import com.fahed.composeapp.di.repositoryModule
import com.fahed.composeapp.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupOnDebug()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AppApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewmodelModule,
                    dataBaseModule)
            )
        }
    }
}