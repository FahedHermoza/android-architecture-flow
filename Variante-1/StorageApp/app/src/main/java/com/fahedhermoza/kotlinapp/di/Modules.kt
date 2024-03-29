package com.fahedhermoza.kotlinapp.di

import android.app.Application
import androidx.room.Room
import com.fahedhermoza.kotlinapp.data.ProductDatabaseRepository
import com.fahedhermoza.kotlinapp.data.storage.ProductDataSource
import com.fahedhermoza.kotlinapp.data.storage.db.ProductDao
import com.fahedhermoza.kotlinapp.data.storage.db.ProductDatabase
import com.fahedhermoza.kotlinapp.data.storage.db.ProductDatabaseDataSource
import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.usecase.AddProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.ClearProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.FetchProductUseCase
import com.fahedhermoza.kotlinapp.domain.usecase.UpdateProductUseCase
import com.fahedhermoza.kotlinapp.presentation.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.*
import org.koin.android.viewmodel.dsl.viewModel

/*** Recurso:
 * https://medium.com/@harmittaa/setting-up-koin-2-0-1-for-android-ebf11de01816
 * https://blog.mindorks.com/kotlin-koin-tutorial ***/
val repositoryModule = module {

    single<ProductDataSource>{ProductDatabaseDataSource()}
    single<ProductRepository>{ProductDatabaseRepository(get())}

}

val viewmodelModule = module {

    single { FetchProductUseCase(get()) }
    single { ClearProductUseCase(get()) }
    single { AddProductUseCase(get()) }
    single { UpdateProductUseCase(get()) }
    viewModel { ProductViewModel(get(), get(), get(), get()) }

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