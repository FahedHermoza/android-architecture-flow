package com.fahed.composeapp.presentation.ui.screen.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductScreen(
    viewModel:ProductViewModel = getViewModel(),
    onEditProductClick: (id: Int) -> Unit = {},
    onAddProductClick: () -> Unit = {},
){
    val products = viewModel.onProducts.observeAsState().value
    LaunchedEffect(key1 = products.isNullOrEmpty()  ){ // Will review
        viewModel.loadProducts()
    }
    Scaffold() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = it.calculateBottomPadding()) //Fixed last element of the list
        ){
            ListBasic(products = products?: emptyList(), onEditProductClick)
            //ListAdvanceList(getTestProducts())
        }
    }
}
/*
@Composable
fun ProductScreen(
    viewModel:ProductViewModel = getViewModel(),
    onEditProductClick: (id: Int) -> Unit = {},
    onAddProductClick: () -> Unit = {},
){
    val products = viewModel.onProducts.observeAsState().value
    LaunchedEffect(key1 = products.isNullOrEmpty()  ){ // Will review
        viewModel.loadProducts()
    }
    Scaffold(
        topBar = { TopAppBarList() },
        floatingActionButton = {
            FabCustom(
                imageVector = Icons.Default.Add,
                onClick = {
                    onAddProductClick()
                })
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = it.calculateBottomPadding()) //Fixed last element of the list
        ){
            ListBasic(products = products?: emptyList(), onEditProductClick)
            //ListAdvanceList(getTestProducts())
        }
    }
}

@Composable
fun TopAppBarList(viewModel:ProductViewModel = getViewModel()) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = {
                viewModel.deleteAllProducts()
                Toast.makeText(context, R.string.help_option_delete, Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.help_options))
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ComposeAppTheme {
        ProductScreen()
    }
}*/