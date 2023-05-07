package com.fahedhermoza.kotlinapp.presentation.viewmodel

import com.fahedhermoza.kotlinapp.domain.model.Product

data class ProductViewState(
    val loading: Boolean = true,
    val products: List<Product> = emptyList(),
)
