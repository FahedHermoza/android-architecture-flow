package com.fahed.composeapp.presentation.ui.screen.editProduct

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.CustomSpacer
import com.fahed.composeapp.core.ui.component.LoadingAlertDialog
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.presentation.ui.screen.addProduct.isValid
import com.fahed.composeapp.presentation.ui.screen.addProduct.observerError
import com.fahed.composeapp.presentation.ui.screen.addProduct.observerLoading
import com.fahed.composeapp.presentation.ui.screen.addProduct.observerSuccess
import com.fahed.composeapp.presentation.viewmodel.AddProductViewModel
import com.fahed.composeapp.presentation.viewmodel.EditProductViewModel
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditScreen(navController: NavController,
               viewModel: EditProductViewModel = getViewModel(),
               objectIdProductTypeArg: String,
               nameProductTypeArg:String,
               descriptionProductTypeArg:String,
               costProductTypeArg: Float,
               logoProductTypeArg: String,
               codeProductTypeArg: String

) {
    val context = LocalContext.current
    val product = remember(objectIdProductTypeArg, nameProductTypeArg, descriptionProductTypeArg, costProductTypeArg, logoProductTypeArg, codeProductTypeArg) {
        Product(objectIdProductTypeArg, nameProductTypeArg, descriptionProductTypeArg, costProductTypeArg.toDouble(), logoProductTypeArg, codeProductTypeArg)
    }
    var titleValue by remember { mutableStateOf(product.name ?: "") }
    var costValue by remember { mutableStateOf(roundedTwoDecimals(product.cost ?: 0.0).toString()) }

    //intiViewModel
    var loadingValue by remember { mutableStateOf(false) }
    observerSuccess(navController = navController, viewModel = viewModel)
    observerLoading(viewModel = viewModel){ loadingValue = it }
    observerError(viewModel = viewModel, context = context)

    //setupUI
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title_edit_product_screen)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.return_toolbar_screen),
                            tint = Color.White
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.common_padding_min))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = titleValue,
                onValueChange = { titleValue = it },
                label = { Text(text = stringResource(R.string.title_add_product_screen)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            CustomSpacer()
            TextField(value = costValue,
                onValueChange = { costValue = it},
                label = { Text(text = stringResource(R.string.cost_add_product_screen)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            CustomSpacer(size = R.dimen.common_padding_max)
            Button(
                onClick = {
                    try{
                        if (isValid(title = product?.name ?: "", cost = product?.cost ?: 0.0)) {
                            viewModel.editProduct(title = titleValue,
                                cost = costValue.toDouble(),
                                product = product)
                        } else {
                            Toast.makeText(context, R.string.error_edit_product_screen, Toast.LENGTH_SHORT).show()
                        }
                    }catch (e: Exception){
                        Timber.e("EditScreen: ${e.message}")
                        Toast.makeText(context, R.string.error_add_product_screen, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button_height))
            ) { Text(text = stringResource(id = R.string.button_edit_product_screen)) }
            LoadingAlertDialog(show = loadingValue)
        }
    }
}

@Composable
fun observerSuccess(navController: NavController, viewModel: EditProductViewModel) {
    val onSuccess = viewModel.onSuccess.observeAsState().value
    LaunchedEffect(onSuccess) {
        onSuccess?.let {
            navController.popBackStack()
        }
    }
}

@Composable
fun observerLoading(viewModel: EditProductViewModel, onValueChanged: (Boolean) -> Unit ) {
    val isLoading by viewModel.onLoading.observeAsState(initial = false)
    LaunchedEffect(isLoading) {
        isLoading?.let {
            onValueChanged(it)
        }
    }
}

@Composable
fun observerError(viewModel: EditProductViewModel, context: Context) {
    val onError by viewModel.onError.observeAsState(initial = "initial")
    LaunchedEffect(onError) {
        onError?.let {
            if(onError!="initial")
                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}

fun roundedTwoDecimals(number: Double): Double {
    return String.format("%.2f", number).toDouble()
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditScreenPreview() {
    ComposeAppTheme {
        //EditScreen(navController = rememberNavController(),
        //    idProductType = "0")
    }
}*/