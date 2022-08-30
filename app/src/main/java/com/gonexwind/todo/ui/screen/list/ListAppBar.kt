package com.gonexwind.todo.ui.screen.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.Priority
import com.gonexwind.todo.ui.components.PriorityItem
import com.gonexwind.todo.ui.theme.TOP_APP_BAR_HEIGHT
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.SearchAppBarState
import com.gonexwind.todo.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> DefaultListAppBar(
            onSearchClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
            },
            onSortClicked = {},
            onDeleteClicked = {},
        )
        else -> SearchAppBar(
            text = searchTextState,
            onTextChange = { sharedViewModel.searchTextState.value = it },
            onCloseClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                sharedViewModel.searchTextState.value = ""
            },
            onSearchClicked = { sharedViewModel.searchTasks(it) },
        )
    }
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
fun SearchAction(onClick: () -> Unit) {
    IconButton(onClick) { Icon(Icons.Filled.Search, stringResource(R.string.search_tasks)) }
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
fun DeleteAction(onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(painterResource(R.drawable.ic_option), stringResource(R.string.short_tasks))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState by remember { mutableStateOf(TrailingIconState.READY_TO_DELETE) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    stringResource(R.string.search),
                    modifier = Modifier.alpha(.5f),
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(.5f),
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Search, stringResource(R.string.search))
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        when (trailingIconState) {
                            TrailingIconState.READY_TO_DELETE -> {
                                onTextChange("")
                                trailingIconState = TrailingIconState.READY_TO_CLOSE
                            }
                            TrailingIconState.READY_TO_CLOSE -> {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    trailingIconState = TrailingIconState.READY_TO_DELETE
                                }
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Close, stringResource(R.string.close))
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
        )
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

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        text = stringResource(R.string.search),
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
    )
}
