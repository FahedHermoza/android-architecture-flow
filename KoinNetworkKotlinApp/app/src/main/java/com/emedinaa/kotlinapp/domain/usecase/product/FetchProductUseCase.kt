package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.domain.ProductRepository

class FetchProductUseCase(private val productRepository: ProductRepository) {

    suspend operator fun invoke(token:String) = run{
        productRepository.fetch(token)
    }
}