package com.fahed.composeapp.di

import android.app.Application
import com.fahed.composeapp.data.AuthenticationRemoteRepository
import com.fahed.composeapp.data.ProductPreferencesRepository
import com.fahed.composeapp.data.ProductRemoteRepository
import com.fahed.composeapp.data.storage.AuthenticationDataSource
import com.fahed.composeapp.data.storage.ProductDataSource
import com.fahed.composeapp.data.storage.local.PreferencesHelper
import com.fahed.composeapp.data.storage.remote.AuthenticationRemoteDataSource
import com.fahed.composeapp.data.storage.remote.ProductApiClient
import com.fahed.composeapp.data.storage.remote.ProductRemoteDataSource
import com.fahed.composeapp.domain.AuthenticationRepository
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.ProductSessionRepository
import com.fahed.composeapp.domain.usecase.product.AddProductUseCase
import com.fahed.composeapp.domain.usecase.product.ClearProductUseCase
import com.fahed.composeapp.domain.usecase.product.FetchProductUseCase
import com.fahed.composeapp.domain.usecase.product.UpdateProductUseCase
import com.fahed.composeapp.domain.usecase.user.AuthenticateUserUseCase
import com.fahed.composeapp.domain.usecase.user.ClearSessionUseCase
import com.fahed.composeapp.domain.usecase.user.GetObjectIdUseCase
import com.fahed.composeapp.domain.usecase.user.GetSessionUseCase
import com.fahed.composeapp.domain.usecase.user.SaveSessionUseCase
import com.fahed.composeapp.domain.usecase.user.VerifySessionUseCase
import com.fahed.composeapp.presentation.viewmodel.AddProductViewModel
import com.fahed.composeapp.presentation.viewmodel.EditProductViewModel
import com.fahed.composeapp.presentation.viewmodel.LoginViewModel
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {

    single<AuthenticationDataSource> { AuthenticationRemoteDataSource() }
    single<AuthenticationRepository> { AuthenticationRemoteRepository(get()) }

    single<ProductDataSource> { ProductRemoteDataSource() }
    single<ProductRepository> { ProductRemoteRepository(get()) }

    single { PreferencesHelper(androidContext()) }
    single<ProductSessionRepository> { ProductPreferencesRepository(get()) }
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

    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProductViewModel(get(), get(), get()) }
    viewModel { AddProductViewModel(get(), get(), get()) }
    viewModel { EditProductViewModel(get(), get()) }

}

const val API_BASE_URL = "https://api.backendless.com/"
val networkModule = module {

    var productApiClient: ProductApiClient? = null

    fun provideHttpClient(interceptor: HttpLoggingInterceptor): ProductApiClient? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        productApiClient = retrofit.create(
            ProductApiClient::class.java
        )

        return productApiClient as ProductApiClient
    }

    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    single { provideInterceptor() }
    single { provideHttpClient(get()) }
}
