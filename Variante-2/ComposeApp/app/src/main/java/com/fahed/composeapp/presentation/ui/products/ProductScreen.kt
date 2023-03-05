package com.fahed.composeapp.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.FabCustom
import com.fahed.composeapp.core.component.ListBasic
import com.fahed.composeapp.core.theme.ComposeAppTheme
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.navigateSingleTopTo
import timber.log.Timber


@Composable
fun ProductScreen(
    onAddProductClick: () -> Unit,
    onEditProductClick: () -> Unit,
    navController: NavController
){
    Scaffold(
        floatingActionButton = {
            FabCustom(
                imageVector = Icons.Default.Add,
                onClick = {
                    navController.navigate(AddProduct.route)
                    //onAddProductClick
                    Log.e("TAG","onAddProductClick")
                })
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(modifier = Modifier.fillMaxWidth()
            .padding(bottom = it.calculateBottomPadding()) //Fixed last element of the list
        ){
            ListBasic(products = getTestProducts(), onEditProductClick, navController)
            //ListAdvanceList(getTestProducts())
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

fun getTestProducts():List<Product>{
    return listOf(
        Product(id=0, name= "Zoro Ronoa", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko))
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ComposeAppTheme {
        //ProductScreen()
    }
}