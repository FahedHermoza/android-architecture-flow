package com.fahed.composeapp.presentation.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.FabCustom
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import com.fahed.composeapp.ui.theme.ComposeAppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductScreen(
    viewModel:ProductViewModel = getViewModel(),
    onEditProductClick: (objectId: String, name:String, description:String, cost:Float, logo:String, code:String) -> Unit = { _, _, _, _, _, _ ->},
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
            ListBasic(products = products?: emptyList(), onEditProductClick = onEditProductClick)
            //ListAdvanceList(getTestProducts())
        }
    }
}

@Composable
fun TopAppBarList(viewModel:ProductViewModel = getViewModel()) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_product_screen)) },
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
}