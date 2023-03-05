package com.fahed.composeapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val icon: ImageVector
    val route: String
}

object Products : AppDestination {
    override val icon = Icons.Filled.Add
    override val route = "products"
}

object AddProduct : AppDestination {
    override val icon = Icons.Filled.Add
    override val route = "add_product"
}

object EditProduct : AppDestination {
    override val icon = Icons.Filled.Add
    override val route = "edit_product"
}

// Screens to be displayed in the top RallyTabRow
val screensList = listOf(Products, AddProduct, EditProduct)