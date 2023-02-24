package com.fahed.composeapp.core.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.fahed.composeapp.R

@Composable
fun CustomSpacer(size: Int = R.dimen.common_padding_default) {
    Spacer(modifier = Modifier.height(dimensionResource(size)))
}