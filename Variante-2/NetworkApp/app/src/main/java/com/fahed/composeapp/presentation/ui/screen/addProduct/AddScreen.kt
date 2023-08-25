package com.fahed.composeapp.presentation.ui.screen.addProduct

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.CustomSpacer
import com.fahed.composeapp.core.ui.component.LoadingAlertDialog
import com.fahed.composeapp.presentation.viewmodel.AddProductViewModel
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: AddProductViewModel = getViewModel()
) {
    var titleValue by remember { mutableStateOf("") }
    var costValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    // intiViewModel
    var loadingValue by remember { mutableStateOf(false) }
    observerSuccess(navController, viewModel)
    observerLoading(viewModel) { loadingValue = it }
    observerError(viewModel, context)

    // setupUI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.title_toolbar_add_product_screen)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.return_toolbar_screen),
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.common_padding_max)).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = titleValue, onValueChange = { titleValue = it },
                label = { Text(text = stringResource(R.string.title_add_product_screen)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            CustomSpacer()
            TextField(
                value = costValue, onValueChange = { costValue = it },
                label = { Text(text = stringResource(R.string.cost_add_product_screen)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            CustomSpacer(size = R.dimen.common_padding_max)
            Button(
                onClick = {
                    try {
                        if (isValid(title = titleValue, cost = costValue.toDouble())) {
                            viewModel.addProduct(
                                title = titleValue,
                                cost = costValue.toDouble()
                            )
                        } else {
                            Toast.makeText(context, R.string.error_add_product_screen, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Timber.e("AddScreen: ${e.message}")
                        Toast.makeText(context, R.string.error_add_product_screen, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button_height))
            ) { Text(text = stringResource(R.string.save_add_product_screen)) }
            LoadingAlertDialog(show = loadingValue)
        }
    }
}

@Composable
fun observerSuccess(navController: NavController, viewModel: AddProductViewModel) {
    val onSuccess = viewModel.onSuccess.observeAsState().value
    LaunchedEffect(onSuccess) { // Observer for the onSuccess state
        onSuccess?.let {
            navController.popBackStack()
        }
    }
}

@Composable
fun observerLoading(viewModel: AddProductViewModel, onValueChanged: (Boolean) -> Unit) {
    val isLoading by viewModel.onLoading.observeAsState(initial = false)
    LaunchedEffect(isLoading) { // Observer for the onLoading state
        isLoading?.let {
            onValueChanged(it)
        }
    }
}

@Composable
fun observerError(viewModel: AddProductViewModel, context: Context) {
    val onError by viewModel.onError.observeAsState(initial = "initial")
    LaunchedEffect(onError) { // Observer for the onLoading state
        onError?.let {
            if (onError != "initial")
                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}

fun isValid(title: String, cost: Double): Boolean {
    if (title.isNotEmpty() && cost> 0.0) return true
    return false
}
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    ComposeAppTheme {
        //val mockData = rememberNavController()
        //AddProductScreen(mockData)
    }
}*/