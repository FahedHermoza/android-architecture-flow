package com.emedinaa.kotlinapp.presentation.viewmodel

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner //Error
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.emedinaa.kotlinapp.data.ProductDatabaseRepository
import com.emedinaa.kotlinapp.data.storage.ProductDataSource
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.UpdateProductUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

/***
 * https://stackoverflow.com/questions/48049131/cannot-resolve-symbol-instanttaskexecutorrule/56073388#56073388
 *
 */

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelUnitTests {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var productDataSource: ProductDataSource


    private lateinit var productRepository: ProductRepository

    private lateinit var viewModel: ProductViewModel
    private lateinit var fetchProductUseCase: FetchProductUseCase
    private lateinit var clearProductUseCase: ClearProductUseCase
    private lateinit var addProductUseCase: AddProductUseCase
    private lateinit var updateProductUseCase: UpdateProductUseCase

    private lateinit var productsObserver: Observer<LiveData<List<Product>>>
    @Before
    fun setup() {
        // 1
        //var productDataBase = ProductDatabaseDataSource(productDataSource)
        productRepository = ProductDatabaseRepository(productDataSource)
        fetchProductUseCase = FetchProductUseCase(productRepository)
        clearProductUseCase = ClearProductUseCase(productRepository)
        addProductUseCase = AddProductUseCase(productRepository)
        updateProductUseCase = UpdateProductUseCase(productRepository)
        //viewModel = ProductViewModel(fetchProductUseCase, clearProductUseCase, addProductUseCase, updateProductUseCase)

        productsObserver = mock()
        //viewModel.loadProducts().observeForever(fetchProductUseCase)
    }

    @Test
    fun init_shouldLoadProducts() {
        viewModel = ProductViewModel(fetchProductUseCase, clearProductUseCase, addProductUseCase, updateProductUseCase)

        verify(fetchProductUseCase).invoke()
        verify(productDataSource).notes()
    }

}