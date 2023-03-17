package com.emedinaa.kotlinapp.presentation.viewmodel

import com.emedinaa.kotlinapp.domain.model.Product

data class ProductViewState(
    val loading: Boolean = true,
    val products: List<Product> = emptyList(),
)
