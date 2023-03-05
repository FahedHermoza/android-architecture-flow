package com.fahed.composeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fahed.composeapp.core.theme.ComposeAppTheme
import com.fahed.composeapp.presentation.ui.AddProduct
import com.fahed.composeapp.presentation.ui.AddProductScreen
import com.fahed.composeapp.presentation.ui.EditProduct
import com.fahed.composeapp.presentation.ui.EditScreen
import com.fahed.composeapp.presentation.ui.ProductScreen
import com.fahed.composeapp.presentation.ui.Products
import com.fahed.composeapp.presentation.ui.screensList
import timber.log.Timber

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*ComposeAppTheme {
                val navController = rememberNavController()
                //val currentBackStack by navController.currentBackStackEntryAsState()
                //val currentDestination = currentBackStack?.destination
                //val currentScreen = screensList.find { it.route == currentDestination?.route } ?: Products

                Scaffold(
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Products.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // builder parameter will be defined here as the graph
                        composable(route = Products.route) {
                            ProductScreen(
                                onAddProductClick = {
                                    navController.navigateSingleTopTo(AddProduct.route)
                                },
                                onEditProductClick = {
                                    navController.navigateSingleTopTo(EditProduct.route)
                                }
                            )
                        }
                        composable(route = AddProduct.route) {
                            AddProductScreen()
                        }
                        composable(route = EditProduct.route) {
                            EditScreen()
                        }
                    }
                }
            }*/
            Navigation()
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
                    //navController.navigateSingleTopTo(AddProduct.route)
                },
                onEditProductClick = {
                    //navController.navigateSingleTopTo(EditProduct.route)
                },
                navController)
        }
        composable(route = AddProduct.route) {
            AddProductScreen()
        }
        composable(route = EditProduct.route) {
            EditScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        //Configuration
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
    }