package com.fahed.composeapp.core.ui.component

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fahed.composeapp.R
import java.util.Calendar
import java.util.Date

@Composable
fun AlertDialogInfo( info: String, onDialogChange: (Boolean) -> Unit){ //Function 1rst order
    AlertDialog(onDismissRequest = { onDialogChange(false)},
    title = { Text(text = stringResource(id = R.string.dialog_title))},
        text = { Text(text = info)},
        confirmButton = {
            TextButton(onClick = { onDialogChange(true) }) {
                Text(text = stringResource(id = R.string.dialog_ok))
            }
        }, dismissButton = {
            TextButton(onClick = { onDialogChange(false) }) {
                Text(text = stringResource(id = R.string.dialog_cancel))
            }
        })
}
//This will be fix
@Composable
fun LoadingAlertDialog(show: Boolean) {
    val openDialog = remember { mutableStateOf(false) }

    if (show) {
        openDialog.value = true
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(stringResource(id = R.string.dialog_loading)) },
            text = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(stringResource(id = R.string.dialog_loading_subtitle))
                }
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(stringResource(id = R.string.dialog_loading_cancel))
                }
            }
        )
    }
}

