package com.fahed.networkapp.presentation.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val route: String
}

object Login : AppDestination {
    override val route = "login"
}

object Products : AppDestination {
    override val route = "products"
}

object AddProduct : AppDestination {
    override val route = "add_product"
}

object EditProduct : AppDestination {
    override val route = "edit_product"
    const val idProductTypeArg = "id_product_type"
    val routeWithArgs = "${route}/{${idProductTypeArg}}"
    val arguments = listOf(
        navArgument(idProductTypeArg) { type = NavType.StringType }
    )
}