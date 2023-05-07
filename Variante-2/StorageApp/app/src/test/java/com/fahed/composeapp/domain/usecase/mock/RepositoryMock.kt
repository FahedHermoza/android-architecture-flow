package com.fahed.composeapp.domain.usecase.mock

import com.fahed.composeapp.domain.model.Product


class RepositoryMock {
    companion object{
        fun product(): Product =
            Product(id = 0, name = "Test", cost = 20.0, description = "Some description of product", logo = 6969)

        fun emptyListProduct() =  emptyList<Product>()

        fun listProduct() =  listOf<Product>(product(), product(), product())
    }
}