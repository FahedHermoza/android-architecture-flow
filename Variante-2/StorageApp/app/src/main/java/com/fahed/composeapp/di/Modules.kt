package com.fahed.composeapp.di

import android.app.Application
import androidx.room.Room
import com.fahed.composeapp.data.ProductDatabaseRepository
import com.fahed.composeapp.data.storage.ProductDataSource
import com.fahed.composeapp.data.storage.db.ProductDao
import com.fahed.composeapp.data.storage.db.ProductDatabase
import com.fahed.composeapp.data.storage.db.ProductDatabaseDataSource
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.usecase.AddProductUseCase
import com.fahed.composeapp.domain.usecase.ClearProductUseCase
import com.fahed.composeapp.domain.usecase.FetchProductUseCase
import com.fahed.composeapp.domain.usecase.GetProductUseCase
import com.fahed.composeapp.domain.usecase.UpdateProductUseCase
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*** Recurso:
 * https://medium.com/@harmittaa/setting-up-koin-2-0-1-for-android-ebf11de01816
 * https://blog.mindorks.com/kotlin-koin-tutorial
 * https://insert-koin.io/docs/reference/koin-android/compose (will review instance get)
 * ***/
val repositoryModule = module {

    single<ProductDataSource>{ ProductDatabaseDataSource() }
    single<ProductRepository>{ ProductDatabaseRepository(get()) }

}

val viewmodelModule = module {

    single { FetchProductUseCase(get()) }
    single { ClearProductUseCase(get()) }
    single { AddProductUseCase(get()) }
    single { UpdateProductUseCase(get()) }
    single { GetProductUseCase(get()) }
    viewModel { ProductViewModel(get(), get(), get(), get(), get()) }
}


val dataBaseModule = module {
    /*** Soporta concurrencia ***/
    val lock = Any()
    val DB_NAME = "Product.db"
    var INSTANCE: ProductDatabase? = null

    fun provideDatabase(application: Application): ProductDatabase {
        synchronized(lock) {
            if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(application, ProductDatabase::class.java, DB_NAME)
                            .build() }
        }
        return INSTANCE!!
    }

    fun provideProductDao(database: ProductDatabase): ProductDao {
        return  database.productDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideProductDao(get()) }

}