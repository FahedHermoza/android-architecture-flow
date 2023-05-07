package com.fahedhermoza.kotlinapp.domian.usecase

import com.fahedhermoza.kotlinapp.domain.ProductRepository
import com.fahedhermoza.kotlinapp.domain.usecase.ClearProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
class ClearProductUseCaseTest {
    // UseCase
    private lateinit var useCase: ClearProductUseCase
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
        useCase = ClearProductUseCase(repository)
    }

    @Test
    fun`check_invoke`() = runTest{
        //given

        //when
        buildUseCase()
        useCase.invoke()
        advanceUntilIdle()

        //then
        Mockito.verify(repository, Mockito.times(1)).deleteAllProduct()
    }
}