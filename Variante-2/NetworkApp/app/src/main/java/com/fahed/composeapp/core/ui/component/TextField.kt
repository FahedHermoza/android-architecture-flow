package com.fahed.composeapp.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fahed.composeapp.R

@Composable
fun PasswordOutlinedTextField(setText: String = "", onValueChanged: (String) -> Unit) {
    var textValue by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = setText.ifBlank { textValue },
        onValueChange = {
            textValue = it
            onValueChanged(textValue)
        },
        label = { Text(text = stringResource(R.string.password_login_screen)) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation =
        if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { hidden = !hidden }) {
                val vector = painterResource(
                    if (hidden) R.drawable.ic_visibility_on
                    else R.drawable.ic_visibility_off
                )
                val description =
                    if (hidden) stringResource(R.string.hide_password_login_screen)
                    else stringResource(R.string.show_password_login_screen)
                Icon(painter = vector, contentDescription = description, modifier = Modifier.size(24.dp))
            }
        }
    )
}