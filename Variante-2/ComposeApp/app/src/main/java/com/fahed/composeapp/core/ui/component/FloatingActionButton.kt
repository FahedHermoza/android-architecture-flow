package com.fahed.composeapp.core.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun FabCustom(imageVector: ImageVector, contentDescription: String = "", isMiniSize: Boolean = false, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(if(isMiniSize) MiniFabSize else DefaultFabSize)
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

private val MiniFabSize = 40.dp
private val DefaultFabSize = 56.dp