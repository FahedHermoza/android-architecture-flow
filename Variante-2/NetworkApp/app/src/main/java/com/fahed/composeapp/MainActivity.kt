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
import com.fahed.composeapp.presentation.ui.screen.login.LoginScreen
import com.fahed.composeapp.presentation.ui.screen.product.ProductScreen
import com.fahed.composeapp.ui.theme.ComposeAppTheme
import com.fahed.networkapp.presentation.ui.screen.Login
import com.fahed.networkapp.presentation.ui.screen.Products

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
        startDestination = Login.route
    ) {
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLoginClick = {
                    navController.navigateSingleTopTo(Products.route)
                })
        }
        composable(route = Products.route) {
            ProductScreen(
                /*onAddProductClick = {
                    navController.navigateSingleTopTo(AddProduct.route)
                },
                onEditProductClick = {
                        id -> navController.navigateToEditProduct(idProductType = "$id")
                }*/)
        }/*
        composable(route = AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(route = EditProduct.routeWithArgs,
            arguments = EditProduct.arguments) {
                navBackStackEntry ->
            val idProductType = navBackStackEntry.arguments?.getString(EditProduct.idProductTypeArg) ?: ""
            EditScreen(navController = navController, idProductType = idProductType)
        }*/
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }
/*
private fun NavHostController.navigateToEditProduct(idProductType: String) {
    this.navigateSingleTopTo("${EditProduct.route}/$idProductType")
}*/

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