package com.fahed.composeapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fahed.composeapp.core.utils.getOrAwaitValue
import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.usecase.AddProductUseCase
import com.fahed.composeapp.domain.usecase.ClearProductUseCase
import com.fahed.composeapp.domain.usecase.FetchProductUseCase
import com.fahed.composeapp.domain.usecase.GetProductUseCase
import com.fahed.composeapp.domain.usecase.UpdateProductUseCase
import com.fahed.composeapp.domain.usecase.mock.RepositoryMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

/***
 * https://stackoverflow.com/questions/48049131/cannot-resolve-symbol-instanttaskexecutorrule/56073388#56073388
 *
 */

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductViewModelUnitTests {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private var fetchProductUseCase = Mockito.mock(FetchProductUseCase::class.java)
    private val clearProductUseCase = Mockito.mock(ClearProductUseCase::class.java)
    private val addProductUseCase = Mockito.mock(AddProductUseCase::class.java)
    private val updateProductUseCase = Mockito.mock(UpdateProductUseCase::class.java)
    private val getProductUseCase = Mockito.mock(GetProductUseCase::class.java)

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
            updateProductUseCase,
            getProductUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check initLoadProduct with some element`()= runTest {
        //given
        val expectedProducts = flowOf(RepositoryMock.listProduct())
        val productRepository = Mockito.mock(ProductRepository::class.java)
        whenever(productRepository.getAllProducts()).thenReturn(expectedProducts)
        fetchProductUseCase = FetchProductUseCase(productRepository)
        //when
        buildViewModel()
        productViewModel.loadProducts()
        val onProduct = productViewModel.onProducts.getOrAwaitValue()
        //then
        Assert.assertEquals(onProduct, RepositoryMock.listProduct())
    }

    @Test
    fun `check addNewProduct is add product`()= runTest {
        //given
        val mock = RepositoryMock.product()
        mock.logo = 2131165272 //Icon by default
        //when
        buildViewModel()
        productViewModel.addProduct(title = mock.name, cost = mock.cost, description = mock.description)
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
        productViewModel.editProduct(product = mock)
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