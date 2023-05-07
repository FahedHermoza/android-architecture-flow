package com.fahed.composeapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.FabCustom
import com.fahed.composeapp.core.component.ListBasic
import com.fahed.composeapp.core.ui.theme.ComposeAppTheme
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
    Scaffold(
        topBar = { TopAppBarList()},
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
}