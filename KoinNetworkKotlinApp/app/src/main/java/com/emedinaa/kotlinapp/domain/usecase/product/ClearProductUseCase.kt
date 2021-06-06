package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class ClearProductUseCase(private val productRepository: ProductRepository) {

    suspend operator fun invoke(token:String, minimalCost: Double) = run{
        productRepository.clear(token, minimalCost)
    }
}