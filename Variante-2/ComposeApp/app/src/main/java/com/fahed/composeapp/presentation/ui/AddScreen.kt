package com.fahed.composeapp.presentation.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.fahed.composeapp.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddProductScreen(navController: NavController,
                     viewModel: ProductViewModel = getViewModel()) {
    var titleValue by remember { mutableStateOf("") }
    var costValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title_new_product)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.help_action_back),
                            tint = androidx.compose.ui.graphics.Color.White)
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
            ) { titleValue = it }
            CustomSpacer()
            TfCustom(
                paddingTop = dimensionResource(id = R.dimen.common_padding_default),
                labelRes = R.string.cost_add_product_fragment,
                isRequired = true,
                minValue = integerResource(id = R.integer.height_min_value),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            ) { costValue = it }
            CustomSpacer(size = R.dimen.common_padding_max)
            Button(
                onClick = {
                    if (isValid(title = titleValue, cost = costValue.toDouble())) {
                        viewModel.addProduct(
                            title = titleValue,
                            cost = costValue.toDouble(),
                            description = ""
                        )
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, R.string.help_invalid_data, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button_height))
            ) { Text(text = stringResource(id = R.string.button_add_product_fragment)) }
        }
    }
}

fun isValid(title:String, cost:Double): Boolean{
    if(title.isNotEmpty() && cost>0.0) return true
    return false
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    ComposeAppTheme {
        //val mockData = rememberNavController()
        //AddProductScreen(mockData)
    }
}