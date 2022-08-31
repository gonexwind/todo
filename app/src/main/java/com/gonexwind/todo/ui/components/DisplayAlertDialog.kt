package com.gonexwind.todo.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gonexwind.todo.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = {
                    onConfirmClicked()
                    closeDialog()
                }) { Text(stringResource(R.string.yes)) }
            },
            dismissButton = {
                OutlinedButton({ closeDialog() }) { Text(stringResource(R.string.no)) }
            },
            onDismissRequest = { closeDialog() },
        )
    }
}