package com.fahed.composeapp.presentation.ui

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
    const val objectIdProductTypeArg = "objectId_product_type"
    const val nameProductTypeArg = "name_product_type"
    const val descriptionProductTypeArg = "description_product_type"
    const val costProductTypeArg = "cost_product_type"
    const val logoProductTypeArg = "logo_product_type"
    const val codeProductTypeArg = "code_product_type"
    val routeWithArgs = "$route/{$objectIdProductTypeArg}/{$nameProductTypeArg}/{$descriptionProductTypeArg}/{$costProductTypeArg}/{$logoProductTypeArg}/{$codeProductTypeArg}"
    // NavType cann't be used with DoubleType, for this reason we use FloatType
    val arguments = listOf(
        navArgument(objectIdProductTypeArg) { type = NavType.StringType },
        navArgument(nameProductTypeArg) { type = NavType.StringType },
        navArgument(descriptionProductTypeArg) { type = NavType.StringType },
        navArgument(costProductTypeArg) { type = NavType.FloatType },
        navArgument(logoProductTypeArg) { type = NavType.StringType },
        navArgument(codeProductTypeArg) { type = NavType.StringType }
    )
}