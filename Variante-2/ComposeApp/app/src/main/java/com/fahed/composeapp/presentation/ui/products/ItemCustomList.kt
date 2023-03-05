package com.fahed.composeapp.core.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.fahed.composeapp.R
import com.fahed.composeapp.domain.model.Product
import com.fahed.composeapp.navigateSingleTopTo
import com.fahed.composeapp.presentation.ui.EditProduct
import timber.log.Timber

@Suppress("UNUSED_EXPRESSION")
@Composable
fun ListBasic(products: List<Product>, onEditProductClick: () -> Unit, navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        products.forEach {
            ItemListBasic(product = it, modifier = Modifier.clickable {
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                navController.navigate(EditProduct.route)
                //onEditProductClick
                Log.e("TAG","Paso")
        }) }
    }
}

@Composable
fun ItemListBasic(product: Product, modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            top = dimensionResource(id = R.dimen.common_padding_min),
            bottom = dimensionResource(id = R.dimen.common_padding_min))) {
        Image(painter = painterResource(id = product.logo),
            contentDescription = stringResource(R.string.content_description_icon_item), modifier = Modifier
                .size(dimensionResource(id = R.dimen.image_icon_size))
                .clip(CircleShape))
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = product.name,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = "S./ ${product.cost}",
            style = MaterialTheme.typography.body2,
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.common_padding_min))
        )
    }
    Divider()
}