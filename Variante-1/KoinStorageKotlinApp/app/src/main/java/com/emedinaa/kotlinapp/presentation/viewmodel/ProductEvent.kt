package com.emedinaa.kotlinapp.presentation.viewmodel

import com.emedinaa.kotlinapp.domain.model.Product
import java.io.Serializable

sealed class ProductEvent: Serializable {
    data class ActionAddNewProduct(val title: String, val cost: Double, val description: String) : ProductEvent()
    data class ActionEditProduct(val title: String, val cost: Double, val product: Product): ProductEvent()
    object ActionDeleteAllProducts: ProductEvent()
}