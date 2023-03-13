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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.FabCustom
import com.fahed.composeapp.core.component.ListBasic
import com.fahed.composeapp.core.ui.theme.ComposeAppTheme
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.navigateSingleTopTo
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel
import timber.log.Timber


@Composable
fun ProductScreen(
    navController: NavController,
    viewModel:ProductViewModel = getViewModel(),
    onEditProductClick: () -> Unit,
    onAddProductClick: () -> Unit,
){
    val products = viewModel.onProducts.observeAsState().value
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
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = it.calculateBottomPadding()) //Fixed last element of the list
        ){
            ListBasic(products = products?: emptyList(), navController, onEditProductClick)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ComposeAppTheme {
        //ProductScreen()
    }
}