package com.fahed.composeapp.domain.usecase

import com.fahed.composeapp.domain.ProductRepository
import com.fahed.composeapp.domain.usecase.mock.RepositoryMock
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
class DeleteProductUseCaseTest {
    // UseCase
    private lateinit var useCase: DeleteProductUseCase
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
        useCase = DeleteProductUseCase(repository)
    }

    @Test
    fun`check_invoke`() = runTest{
        //given
        val repositoryMockProduct = RepositoryMock.product()
        val parameter = DeleteProductUseCase.Params(repositoryMockProduct)

        //when
        buildUseCase()
        useCase.invoke(parameter)
        advanceUntilIdle()

        //then
        Mockito.verify(repository, Mockito.times(1)).deleteProduct(repositoryMockProduct)
    }
}