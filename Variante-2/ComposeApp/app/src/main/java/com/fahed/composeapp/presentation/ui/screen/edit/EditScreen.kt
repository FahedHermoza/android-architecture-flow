package com.fahed.composeapp.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.CustomSpacer
import com.fahed.composeapp.core.component.TfCustom
import com.fahed.composeapp.core.ui.theme.ComposeAppTheme
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditScreen(navController: NavController,
               idProductType: String,
               viewModel: ProductViewModel = getViewModel()
) {
    val context = LocalContext.current
    val product = viewModel.onProductSelected.observeAsState()
    LaunchedEffect(key1 = product.value?.isEmpty() ){
        viewModel.getProduct(idProductType.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title_edit_product)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.help_action_back),
                            tint = Color.White
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.common_padding_min))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TfCustom(
                paddingTop = dimensionResource(id = R.dimen.common_padding_nano),
                labelRes = R.string.title_add_product_fragment,
                isRequired = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                setText = product.value?.name ?: ""
            ) { viewModel.setProduct { product.value?.copy(name = it) }   }
            CustomSpacer()
            TfCustom(
                paddingTop = dimensionResource(id = R.dimen.common_padding_default),
                labelRes = R.string.cost_add_product_fragment,
                isRequired = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                setText = if(product.value?.cost.toString() == "0.0") "" else product.value?.cost.toString()
            ) { viewModel.setProduct { product.value?.copy(cost = if(it.isNotBlank()) it.toDouble() else 0.0) }  }
            CustomSpacer(size = R.dimen.common_padding_max)
            Button(
                onClick = {
                    if (isValid(title = product.value?.name ?: "", cost = product.value?.cost ?: 0.0)) {
                        viewModel.editProduct(product = product.value ?: Product())
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, R.string.help_invalid_data, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button_height))
            ) { Text(text = stringResource(id = R.string.button_edit_product_fragment)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditScreenPreview() {
    ComposeAppTheme {
        //EditScreen(navController = rememberNavController(),
        //    idProductType = "0")
    }
}