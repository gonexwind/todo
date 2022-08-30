package com.gonexwind.todo.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.gonexwind.todo.R
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Action
import com.gonexwind.todo.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) { sharedViewModel.getAllTasks() }

    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackbarHostState,
        { sharedViewModel.handleDatabaseAction(action) },
        { sharedViewModel.action.value = it },
        sharedViewModel.title.value,
        action
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { ListAppBar(sharedViewModel, searchAppBarState, searchTextState) },
        content = { ListContent(allTasks, navigateToTaskScreen) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_task))
            }
        }
    )
}

@Composable
fun DisplaySnackBar(
    snackbarHostState: SnackbarHostState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action,
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = snackbarHostState.showSnackbar(
                    "${action.name} : $taskTitle",
                    setActionLabel(action),
                )
                undoDeletedTask(action, snackbarResult, onUndoClicked)
            }
        }
    }
}

private fun setActionLabel(action: Action): String = if (action.name == "DELETE") "UNDO" else "OK"

private fun undoDeletedTask(
    action: Action,
    snackbarResult: SnackbarResult,
    onClick: (Action) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onClick(Action.UNDO)
    }
}