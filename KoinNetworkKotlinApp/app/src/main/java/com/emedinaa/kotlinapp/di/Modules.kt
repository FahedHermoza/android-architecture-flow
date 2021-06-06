package com.emedinaa.kotlinapp.di

import com.emedinaa.kotlinapp.data.ProductPreferencesRespository
import com.emedinaa.kotlinapp.data.ProductRemoteRepository
import com.emedinaa.kotlinapp.data.remote.AuthenticationDataSource
import com.emedinaa.kotlinapp.data.remote.AuthenticationRemoteDataSource
import com.emedinaa.kotlinapp.data.remote.ProductApiClient
import com.emedinaa.kotlinapp.data.storage.AuthenticationRemoteRepository
import com.emedinaa.kotlinapp.data.storage.ProductDataSource
import com.emedinaa.kotlinapp.data.storage.local.PreferencesHelper
import com.emedinaa.kotlinapp.data.storage.remote.ProductRemoteDataSource
import com.emedinaa.kotlinapp.domain.AuthenticationRepository
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.ProductSessionRepository
import com.emedinaa.kotlinapp.domain.usecase.product.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.product.UpdateProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.user.*
import com.emedinaa.kotlinapp.presentation.viewmodel.AddProductViewModel
import com.emedinaa.kotlinapp.presentation.viewmodel.EditProductViewModel
import com.emedinaa.kotlinapp.presentation.viewmodel.LoginViewModel
import com.emedinaa.kotlinapp.presentation.viewmodel.ProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.*
import org.koin.android.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*** Recurso:
 * https://medium.com/@harmittaa/setting-up-koin-2-0-1-for-android-ebf11de01816
 * https://blog.mindorks.com/kotlin-koin-tutorial ***/
val repositoryModule = module {

    single<AuthenticationDataSource>{AuthenticationRemoteDataSource()}
    single<AuthenticationRepository>{ AuthenticationRemoteRepository(get()) }

    single<ProductDataSource>{ ProductRemoteDataSource() }
    single<ProductRepository>{ ProductRemoteRepository(get()) }
    
    single { PreferencesHelper(androidContext()) }
    single<ProductSessionRepository>{ ProductPreferencesRespository(get()) }
}

val viewmodelModule = module {
    //Product
    single { AddProductUseCase(get()) }
    single { ClearProductUseCase(get()) }
    single { FetchProductUseCase(get()) }
    single { UpdateProductUseCase(get()) }

    //User
    single { AuthenticateUserUseCase(get()) }
    single { ClearSessionUseCase(get()) }
    single { GetObjectIdUseCase(get()) }
    single { GetSessionUseCase(get()) }
    single { SaveSessionUseCase(get()) }
    single { VerifySessionUseCase(get()) }

    viewModel { ProductViewModel(get(), get(), get()) }
    viewModel { AddProductViewModel(get(), get(), get()) }
    viewModel { EditProductViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
}

val networkModule = module {
    val API_BASE_URL = "https://api.backendless.com/"

    var productApiClient: ProductApiClient? = null

    fun provideHttpClient(interceptor: HttpLoggingInterceptor): ProductApiClient?{
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        productApiClient = retrofit.create(
            ProductApiClient::class.java)

        return productApiClient as ProductApiClient
    }

    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    single { provideInterceptor() }
    single { provideHttpClient(get()) }
}

/*
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

}*/