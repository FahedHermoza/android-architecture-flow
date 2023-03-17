package com.fahed.composeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fahed.composeapp.core.ui.theme.ComposeAppTheme
import com.fahed.composeapp.presentation.ui.AddProduct
import com.fahed.composeapp.presentation.ui.AddProductScreen
import com.fahed.composeapp.presentation.ui.EditProduct
import com.fahed.composeapp.presentation.ui.EditScreen
import com.fahed.composeapp.presentation.ui.ProductScreen
import com.fahed.composeapp.presentation.ui.Products

/***
 * https://www.youtube.com/watch?v=eNuaMn4ukdo
 * https://developer.android.com/codelabs/jetpack-compose-navigation?hl=es-419#10
 * https://insert-koin.io/docs/reference/koin-android/viewmodel/#viewmodel-and-injection-parameters
 * https://developer.android.com/jetpack/compose/mental-model?hl=es-419 (Recomposición)
 *
 * Improve following points.
 * 1. Init modules with koin without get()
 * 2. Not function preview of function composable
 */
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                Scaffold{
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Products.route
    ) {
        composable(route = Products.route) {
            ProductScreen(
                onAddProductClick = {
                    navController.navigateSingleTopTo(AddProduct.route)
                },
                onEditProductClick = {
                    id -> navController.navigateToEditProduct(idProductType = "$id")
                })
        }
        composable(route = AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(route = EditProduct.routeWithArgs,
            arguments = EditProduct.arguments) {
                navBackStackEntry ->
            val idProductType = navBackStackEntry.arguments?.getString(EditProduct.idProductTypeArg) ?: ""
            EditScreen(navController = navController, idProductType = idProductType)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }

private fun NavHostController.navigateToEditProduct(idProductType: String) {
    this.navigateSingleTopTo("${EditProduct.route}/$idProductType")
}