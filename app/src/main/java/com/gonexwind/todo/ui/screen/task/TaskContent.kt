package com.gonexwind.todo.ui.screen.task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.Priority
import com.gonexwind.todo.ui.components.PriorityDropDown
import com.gonexwind.todo.ui.theme.LARGE_PADDING
import com.gonexwind.todo.ui.theme.MEDIUM_PADDING
import com.gonexwind.todo.ui.theme.TOP_APP_BAR_HEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LARGE_PADDING)
    ) {
        Divider(Modifier.height(TOP_APP_BAR_HEIGHT), color = MaterialTheme.colorScheme.background)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(stringResource(R.string.title)) },
            singleLine = true,
        )
        Divider(Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colorScheme.background)
        PriorityDropDown(priority, onPrioritySelected)
        Divider(Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colorScheme.background)
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(stringResource(R.string.description)) },
        )
    }
}

@Preview
@Composable
fun TaskContentPreview() {
    TaskContent(
        title = "Fikky",
        onTitleChange = {},
        description = "nani",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}