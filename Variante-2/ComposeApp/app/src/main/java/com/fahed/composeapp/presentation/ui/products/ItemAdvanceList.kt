package com.fahed.composeapp.presentation.ui.products

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import com.fahed.composeapp.R
import com.fahed.composeapp.domain.model.Product

@Composable
fun ListAdvanceList(products: List<Product>) {
    val context = LocalContext.current
    LazyColumn{
        items(products.size){
            val product = products[it]
            ItemListAdvance(product = product, modifier = Modifier.clickable {
                Toast.makeText(context, products[it].name, Toast.LENGTH_SHORT).show()
            })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemListAdvance(product: Product, modifier: Modifier, showSecondaryText: Boolean = true) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(
        top = dimensionResource(id = R.dimen.common_padding_nano),
        bottom = dimensionResource(id = R.dimen.common_padding_nano))) {
        ListItem(
            text = { Text(text = product.name, style = MaterialTheme.typography.body1, color = Color.Black) },
            secondaryText = {
                if(showSecondaryText){
                    Text(text = product.description,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                }
            },
            singleLineSecondaryText = false, //Small space in secondaryText
            icon = {
                Icon(
                    //painter = painterResource(id = R.drawable.ic_funko),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.content_description_icon_item),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_icon_size))
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                width = dimensionResource(id = R.dimen.list_item_img_stroke),
                                color = Color.Green
                            ), CircleShape
                        )
                )
            },
            trailing = {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = dimensionResource(id = R.dimen.common_padding_default)),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = "S./ ${product.cost}",
                        style = MaterialTheme.typography.body2)
                }
            }
        )
        Divider()
    }
}