package com.fahedhermoza.kotlinapp.domian.usecase

import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.usecase.AddProductUseCase
import com.fahedhermoza.kotlinapp.domian.usecase.mock.RepositoryMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddProductUseCaseTest {
    // UseCase
    private lateinit var useCase: AddProductUseCase
    // Mocks
    private val repository = Mockito.mock(ProductRepository::class.java)

    @Before
    fun setup() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildUseCase(repository: ProductRepository = this.repository) {
        useCase = AddProductUseCase(repository)
    }

    @Test
    fun`check_invoke`() = runTest{
        //given
        val repositoryMockProduct = RepositoryMock.product()
        val parameter = AddProductUseCase.Params(repositoryMockProduct)

        //when
        buildUseCase()
        useCase.invoke(parameter)
        advanceUntilIdle()

        //then
        Mockito.verify(repository, Mockito.times(1)).addProduct(repositoryMockProduct)
    }
}