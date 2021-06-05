package com.emedinaa.kotlinapp.domain.usecase

import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class DeleteProductUseCase(private val productDatabaseRepository: ProductRepository) {

    suspend operator fun invoke(product: Product) = run { productDatabaseRepository.deleteProduct(product)}

}