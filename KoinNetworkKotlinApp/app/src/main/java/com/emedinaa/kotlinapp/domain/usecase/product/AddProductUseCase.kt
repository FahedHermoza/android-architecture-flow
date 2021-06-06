package com.emedinaa.kotlinapp.domain.usecase.product

import com.emedinaa.kotlinapp.domain.ProductRepository
import com.emedinaa.kotlinapp.domain.model.Product

class AddProductUseCase(private val productRepository: ProductRepository) {

    suspend operator fun invoke(token:String, product: Product) = run{
        productRepository.add(token, product)
    }
}