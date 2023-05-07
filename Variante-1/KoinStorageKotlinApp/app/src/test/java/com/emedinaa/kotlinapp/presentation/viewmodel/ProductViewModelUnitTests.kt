package com.emedinaa.kotlinapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.usecase.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.UpdateProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.mock.RepositoryMock
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/***
 * https://stackoverflow.com/questions/48049131/cannot-resolve-symbol-instanttaskexecutorrule/56073388#56073388
 *
 */

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelUnitTests {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private var fetchProductUseCase = Mockito.mock(FetchProductUseCase::class.java)
    private val clearProductUseCase = Mockito.mock(ClearProductUseCase::class.java)
    private val addProductUseCase = Mockito.mock(AddProductUseCase::class.java)
    private val updateProductUseCase = Mockito.mock(UpdateProductUseCase::class.java)

    private lateinit var productViewModel: ProductViewModel
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun buildViewModel() {
        productViewModel = ProductViewModel(
            fetchProductUseCase,
            clearProductUseCase,
            addProductUseCase,
            updateProductUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check initLoadProduct with element initialized`()= runBlocking {
        //given
        val expectedProducts = flowOf(RepositoryMock.listProduct())
        val productRepository = Mockito.mock(ProductRepository::class.java)
        whenever(productRepository.getAllProducts()).thenReturn(expectedProducts)
        fetchProductUseCase = FetchProductUseCase(productRepository)
        val firstState = ProductViewState(loading = false, products = RepositoryMock.listProduct())
        //when
        buildViewModel()
        //then
        productViewModel.state.test {
            val emision = awaitItem()
            assertEquals(firstState, emision)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `check initLoadProduct with changing elements`()= runTest{
        //given
        val firstState = ProductViewState(loading = true, products = emptyList())
        val secondState = ProductViewState(loading = false, products = RepositoryMock.listProduct())
        //when
        buildViewModel()
        //then
        productViewModel.state.test {
            productViewModel.loadProductsTest(secondState)
            val emision1 = awaitItem()
            assertEquals(firstState, emision1)

            val emision2 = awaitItem()
            assertEquals(secondState, emision2)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `check addNewProduct is add product`()= runTest {
        //given
        val mock = RepositoryMock.product()
        mock.logo = 2131558400 //Icon by default
        //when
        buildViewModel()
        productViewModel.addNewProduct(title = mock.name, cost = mock.cost, description = mock.description)
        advanceUntilIdle()

        //then
        verify(addProductUseCase, times(1)).invoke(AddProductUseCase.Params(mock))
    }

    @Test
    fun `check editProduct is update product`()= runTest {
        //given
        val mock = RepositoryMock.product()

        //when
        buildViewModel()
        productViewModel.editProduct(title = mock.name, cost = mock.cost, product = mock)
        advanceUntilIdle()

        //then
        verify(updateProductUseCase, times(1)).invoke(UpdateProductUseCase.Params(mock))
    }

    @Test
    fun `check deleteAllProducts is correct`()= runTest {
        //given
        //when
        buildViewModel()
        productViewModel.deleteAllProducts()
        advanceUntilIdle()

        //then
        verify(clearProductUseCase, times(1)).invoke()
    }

}