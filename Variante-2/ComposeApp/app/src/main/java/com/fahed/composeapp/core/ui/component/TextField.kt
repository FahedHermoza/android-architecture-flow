package com.fahed.composeapp.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.fahed.composeapp.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TfCustom(modifier: Modifier = Modifier,
             paddingTop: Dp =  dimensionResource(id = R.dimen.common_padding_default),
             labelRes: Int,
             maxLength: Int? = null,
             isRequired: Boolean = false,
             isSingleLine: Boolean = true,
             minValue: Int = 0,
             errorRes: Int = R.string.help_required,
             isLikeButton: Boolean = false,
             keyboardOptions: KeyboardOptions? = null,
             clearValue: Boolean = false,
             onValueChanged: (String) -> Unit) { //Manipula la logica con funciones de 1er orden
    var textValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    if (clearValue){
        textValue = ""
        onValueChanged(textValue)
    }

    val context = LocalContext.current

    Column(modifier = modifier) {
        val keyboard = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        OutlinedTextField(value = textValue,
            onValueChange = {
                if(maxLength == null)
                    textValue = it
                else {
                    if(it.length<= maxLength){
                        textValue = it
                    }
                }
                //Some validations
                isError = it.isEmpty() and isRequired
                if(minValue > 0) isError = (textValue.toIntOrNull() ?: 0) < minValue

                onValueChanged(textValue)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingTop)
                .clickable { if (isLikeButton) datePickerTextField(context){
                        dateStr -> textValue = dateStr
                    onValueChanged(textValue)
                } },
            label = { Text(text = stringResource(id = labelRes)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardOptions?.keyboardType ?: KeyboardType.Text,
                capitalization = keyboardOptions?.capitalization ?: KeyboardCapitalization.Sentences, //Accept text with first letters in Capital Letter
                imeAction = (if(keyboardOptions == null || keyboardOptions.imeAction == ImeAction.Default)
                    ImeAction.Next else keyboardOptions?.imeAction)!!),
            keyboardActions = KeyboardActions(
                onDone = {keyboard?.hide()},
                //onNext = {focusManager.moveFocus(FocusDirection.Up)} //Control action up of keyboard
                ),
            singleLine = isSingleLine,
            isError = isError, //All component is color red
            readOnly = isLikeButton,
            enabled = !isLikeButton
        )
        if(isRequired){
            Text(text = if (isError) stringResource(id = errorRes)
                        else stringResource(id = R.string.help_required),
                style = MaterialTheme.typography.caption,
                color = if (isError) MaterialTheme.colors.error
                else MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.common_padding_default),
                    top = dimensionResource(id = R.dimen.common_padding_micro)
                ))
        }
    }
}
//Bring the counter update
@Composable
fun CounterMaxLength(currentLength: Int, maxLengthRes: Int){
    Text(text = "$currentLength/${integerResource(id = maxLengthRes)}", modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(id = R.dimen.common_padding_micro)),
        textAlign = TextAlign.Right,
        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
        style = MaterialTheme.typography.caption)
}