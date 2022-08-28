package com.gonexwind.todo.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.Priority
import com.gonexwind.todo.ui.components.PriorityItem

@Composable
fun ListAppBar() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    SmallTopAppBar(
        title = { Text(stringResource(R.string.tasks)) },
        actions = {
            ListAppBarActions(onSearchClicked, onSortClicked, onDeleteClicked)
        },
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAction(onDeleteClicked)
}

@Composable
fun SearchAction(
    onClick: () -> Unit
) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_tasks)
        )
    }
}

@Composable
fun SortAction(
    onClick: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_filter),
            contentDescription = stringResource(R.string.short_tasks),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { PriorityItem(Priority.LOW) },
                onClick = {
                    expanded = false
                    onClick(Priority.LOW)
                }
            )
            DropdownMenuItem(
                text = { PriorityItem(Priority.HIGH) },
                onClick = {
                    expanded = false
                    onClick(Priority.HIGH)
                }
            )
            DropdownMenuItem(
                text = { PriorityItem(Priority.NONE) },
                onClick = {
                    expanded = false
                    onClick(Priority.NONE)
                }
            )
        }
    }
}

@Composable
fun DeleteAction(
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_option),
            contentDescription = stringResource(R.string.short_tasks),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete_all)) },
                onClick = {
                    expanded = false
                    onClick()
                }
            )
        }
    }
}

@Preview
@Composable
fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {},
    )
}
