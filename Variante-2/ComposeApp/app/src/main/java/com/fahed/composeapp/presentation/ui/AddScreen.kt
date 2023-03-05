package com.fahed.composeapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.CustomSpacer
import com.fahed.composeapp.core.component.TfCustom
import com.fahed.composeapp.core.theme.ComposeAppTheme

@Composable
fun AddProductScreen() {
    var titleValue by remember { mutableStateOf("") }
    var costValue by remember { mutableStateOf("") }

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
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) { Text(text = stringResource(id = R.string.button_add_product_fragment)) }
        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    ComposeAppTheme {
        AddProductScreen()
    }
}