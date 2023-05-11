package com.fahed.composeapp.presentation.ui.screen.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.CustomSpacer
import com.fahed.composeapp.presentation.viewmodel.LoginViewModel
import com.fahed.composeapp.ui.theme.ComposeAppTheme
import org.koin.androidx.compose.getViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController,
                onLoginClick: () -> Unit = {},
                viewModel: LoginViewModel = getViewModel()) {
    var userValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    /*val success = viewModel.onSuccess.observeAsState().value
    LaunchedEffect(key1 = (success == true)) {
        navController.navigate("product_screen")
    }*/

    val onSuccess = viewModel.onSuccess.observeAsState().value
    LaunchedEffect(onSuccess) {//Observer for the onSuccess state
       onSuccess?.let {
           onLoginClick()
       }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.common_padding_max))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.icon_login_screen), modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape))
            OutlinedTextField(value = userValue, onValueChange = { userValue = it },
                label = { Text(text = stringResource(R.string.email_login_screen)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = stringResource(R.string.email_login_screen))
                }
            )
            OutlinedTextField(value = passwordValue, onValueChange = { passwordValue = it },
                label = { Text(text = stringResource(R.string.password_login_screen)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Warning, contentDescription = stringResource(R.string.password_login_screen))
                }
            )
            CustomSpacer()
            Button(onClick = {
                if (viewModel.checkInvalidUsername(userValue) or viewModel.checkInvalidPassword(passwordValue))
                    Toast.makeText(context, R.string.error_login_screen, Toast.LENGTH_SHORT).show()
                else {
                    viewModel.login(userValue, passwordValue)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Text(text = stringResource(R.string.button_login_screen))
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    ComposeAppTheme {
        //val mockData = rememberNavController()
        //LoginScreen(mockData)
    }
}