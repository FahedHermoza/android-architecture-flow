package com.fahed.composeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fahed.composeapp.presentation.ui.AddProduct
import com.fahed.composeapp.presentation.ui.EditProduct
import com.fahed.composeapp.presentation.ui.Login
import com.fahed.composeapp.presentation.ui.Products
import com.fahed.composeapp.presentation.ui.screen.addProduct.AddProductScreen
import com.fahed.composeapp.presentation.ui.screen.editProduct.EditScreen
import com.fahed.composeapp.presentation.ui.screen.login.LoginScreen
import com.fahed.composeapp.presentation.ui.screen.product.ProductScreen
import com.fahed.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                Scaffold {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Login.route
    ) {
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLoginClick = {
                    navController.navigateSingleTopTo(Products.route)
                }
            )
        }
        composable(route = Products.route) {
            ProductScreen(
                onAddProductClick = {
                    navController.navigateSingleTopTo(AddProduct.route)
                },
                onEditProductClick = {
                    objectId, name, description, cost, logo, code ->
                    navController.navigateToEditProduct(
                        objectIdProductTypeArg = objectId,
                        nameProductTypeArg = name,
                        descriptionProductTypeArg = description.ifEmpty { "No description" },
                        costProductTypeArg = cost,
                        logoProductTypeArg = logo.ifEmpty { "No logo" },
                        codeProductTypeArg = code
                    )
                }
            )
        }
        composable(route = AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(
            route = EditProduct.routeWithArgs,
            arguments = EditProduct.arguments
        ) {
            navBackStackEntry ->
            val objectIdProductTypeArg = navBackStackEntry.arguments?.getString(EditProduct.objectIdProductTypeArg) ?: ""
            val nameProductTypeArg = navBackStackEntry.arguments?.getString(EditProduct.nameProductTypeArg) ?: ""
            val descriptionProductTypeArg = navBackStackEntry.arguments?.getString(EditProduct.descriptionProductTypeArg) ?: ""
            val costProductTypeArg = navBackStackEntry.arguments?.getFloat(EditProduct.costProductTypeArg) ?: 0.0f
            val logoProductTypeArg = navBackStackEntry.arguments?.getString(EditProduct.logoProductTypeArg) ?: ""
            val codeProductTypeArg = navBackStackEntry.arguments?.getString(EditProduct.codeProductTypeArg) ?: ""

            EditScreen(
                navController = navController, objectIdProductTypeArg = objectIdProductTypeArg,
                nameProductTypeArg = nameProductTypeArg, descriptionProductTypeArg = descriptionProductTypeArg,
                costProductTypeArg = costProductTypeArg, logoProductTypeArg = logoProductTypeArg,
                codeProductTypeArg = codeProductTypeArg
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }

private fun NavHostController.navigateToEditProduct(
    objectIdProductTypeArg: String,
    nameProductTypeArg: String,
    descriptionProductTypeArg: String,
    costProductTypeArg: Float,
    logoProductTypeArg: String,
    codeProductTypeArg: String
) {
    this.navigateSingleTopTo("${EditProduct.route}/$objectIdProductTypeArg/$nameProductTypeArg/$descriptionProductTypeArg/$costProductTypeArg/$logoProductTypeArg/$codeProductTypeArg")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        Greeting("Android")
    }
}