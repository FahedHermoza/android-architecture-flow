package com.fahed.composeapp.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fahed.composeapp.R
import com.fahed.composeapp.core.component.FabCustom
import com.fahed.composeapp.core.component.ListBasic
import com.fahed.composeapp.core.theme.ComposeAppTheme
import com.fahed.composeapp.domain.model.Product

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductScreen(){
    Scaffold(
        floatingActionButton = {
            FabCustom(
                imageVector = Icons.Default.Add,
                onClick = { })
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(modifier = Modifier.fillMaxWidth()
            //.padding(bottom = it.calculateBottomPadding())
        ){ //Fixed last element of the list
            ListBasic(products = getTestProducts())
            //ListAdvanceList(getTestProducts())
        }
    }
}

fun getTestProducts():List<Product>{
    return listOf(
        Product(id=0, name= "Zoro Ronoa", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko),
        Product(id=0, name= "Zoro", cost = 30.9, description = "anime de accion", logo = R.drawable.ic_funko))
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ComposeAppTheme {
        ProductScreen()
    }
}